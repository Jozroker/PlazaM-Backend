package com.site.plazam.controller;

import com.site.plazam.domain.Lang;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.MovieForHomeSliderDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.service.*;
import org.json.JSONObject;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
public class HomeController {

    private final CinemaService cinemaService;

    private final UserService userService;

    private final SeanceService seanceService;

    private final HallService hallService;

    private final MovieService movieService;

    private final LocaleResolver localeResolver;

    public HomeController(CinemaService cinemaService,
                          UserService userService,
                          SeanceService seanceService,
                          HallService hallService,
                          MovieService movieService,
                          LocaleResolver localeResolver) {
        this.cinemaService = cinemaService;
        this.userService = userService;
        this.seanceService = seanceService;
        this.hallService = hallService;
        this.movieService = movieService;
        this.localeResolver = localeResolver;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    @GetMapping({"/home", "/"})
    @Transactional
    public String home(ModelMap model, Principal principal) {
        CinemaDTO cinema = cinemaService.getCinemaByLocation();
        if (principal != null) {
            UserForSelfInfoDTO user =
                    userService.findByUsernameOrEmail(principal.getName(), principal.getName());
            if (user != null) {
                cinema = user.getSelectedCinema();
            }
        }
        Page<Map.Entry> seances = seanceService.findSeancesList(LocalDate.now(),
                cinemaService.findById(cinema.getId()), null, null,
                PageRequest.of(0, 8));
        List<MovieForHomeSliderDTO> homeSliderMovies =
                movieService.findMovieForHomeSliderByReleaseDateBeforeOrderByReleaseDate(LocalDate.now());
        model.addAttribute("pagesCount", seances.getTotalPages());
        model.addAttribute("sliderMovies", homeSliderMovies);
        return "home";
    }

    @GetMapping("/footer")
    public String footer() {
        return "footer";
    }

    @GetMapping("/filter")
    public String filter() {
        return "filter";
    }

    @GetMapping("/header")
    @Transactional
    public String header(@RequestParam(name = "path", required = false) String path,
                         ModelMap model,
                         Principal principal,
                         HttpServletRequest request, HttpServletResponse response
    ) {
        Lang currentLang = Lang.ENG;
        CinemaDTO currentCinema = cinemaService.findAll().get(0);
        try {
            currentLang =
                    Lang.valueOf(LocaleContextHolder.getLocale().getISO3Language().toUpperCase());
        } catch (Exception ignore) {
        }

        try {
            currentCinema = cinemaService.getCinemaByLocation();
        } catch (Exception ignore) {
        }
//        LocaleContextHolder.setLocale(Locale.forLanguageTag("en-US"));
//        localeResolver.setLocale(request, response, Locale.forLanguageTag("en-US"));

//        String langTag;
//        localeResolver.setLocale(request, response, Locale.forLanguageTag("en-US"));


//        model.addAttribute("user", userService.findByUsernameOrEmail("admin",
//                "admin@gmail.com"));

        if (principal != null) {
            UserForSelfInfoDTO user =
                    userService.findByUsernameOrEmail(principal.getName(),
                            principal.getName());
            if (user != null) {
                if (user.getSelectedCinema() != null) {
                    currentCinema = user.getSelectedCinema();
                }
                model.addAttribute("user", user);
            }
        }


        model.addAttribute("currentCinema", currentCinema);
        model.addAttribute("currentLanguage", currentLang);

//        Page<Map.Entry> seances =  seanceService.findSeancesList(LocalDate.now(),
//                currentCinema, PageRequest.of(page.orElse(0), 8));
//        model.addAttribute("seances", getSeanceMap(seances));
//        model.addAttribute("pages", seances.getTotalPages());
//
//        model.addAttribute("movieForSlider",
//                movieService.findMovieForHomeSliderByReleaseDateBeforeOrderByReleaseDate(LocalDate.now()));
        //todo multithreading for different requests
        return "header";
    }

    @GetMapping("/cinemas/header")
    @ResponseBody
    @Transactional
    public String getCinemasHeader() {
        Map<String, List<CinemaDTO>> cinemaMap = new HashMap<>();
        cinemaService.findAll().forEach(cinema -> {
            if (!cinemaMap.containsKey(cinema.getCountry().name())) {
                cinemaMap.put(cinema.getCountry().name(),
                        new ArrayList<>());
            }
            cinemaMap.get(cinema.getCountry().name()).add(cinema);
        });
        return new JSONObject(cinemaMap).toString();
    }

    @PostMapping("/cinema")
    @Transactional
    public String setCinema(@RequestParam String cinemaId,
                            @ModelAttribute(name = "currentCinema") CinemaDTO currentCinema, ModelMap model) {
        CinemaDTO cinema = cinemaService.findById(cinemaId);
        model.addAttribute("currentCinema", cinema);
        if (cinema.getCity().equals("a")) {
            System.out.println("a");
        }
        return "success";
    }

    @GetMapping(value = "/cinemas/footer")
    @ResponseBody
    @Transactional
    public String getCinemasFooter() {
        Map<String, List<String>> cinemaMap = new HashMap<>();
        cinemaService.findAll().forEach(cinema -> {
            if (!cinemaMap.containsKey(cinema.getCountry().name())) {
                cinemaMap.put(cinema.getCountry().name(),
                        new ArrayList<>());
            }
            cinemaMap.get(cinema.getCountry().name()).add(cinema.getStreet());
        });
        return new JSONObject(cinemaMap).toString();
    }

//    @GetMapping("/footer")
//    @Transactional
//    public String footer()

    @GetMapping("/halls")
    @ResponseBody
    public List<HallForSeanceDTO> getHallsByCinema(@RequestParam(required =
            false) String cinemaId, ModelMap model) {
        model.addAttribute("currentCinema",
                cinemaService.getCinemaByLocation());
        CinemaDTO currentCinema = cinemaId == null ?
                (CinemaDTO) model.getAttribute(
                        "currentCinema") : cinemaService.findById(cinemaId);
        return hallService.findHallForSeanceByCinema(currentCinema);
    }

    @GetMapping({"/home/page/{page}", "/page/{page}"})
    @ResponseBody
    public List<List<Object>> getSeancesPage(@PathVariable int page,
                                             @RequestParam String cinemaId,
                                             @RequestParam(required = false) String genres,
                                             @RequestParam(required = false) String technologies) {
        Page<Map.Entry> seances;
        if (cinemaId == null) {
            cinemaId = cinemaService.getCinemaByLocation().getId();
        }
        if (genres != null && technologies != null) {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
                    Arrays.asList(genres.split(",").clone()),
                    PageRequest.of(page - 1, 8));
        } else if (genres != null) {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), null,
                    Arrays.asList(genres.split(",").clone()),
                    PageRequest.of(page - 1, 8));
        } else if (technologies != null) {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
                    null, PageRequest.of(page - 1, 8));
        } else {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), null,
                    null, PageRequest.of(page - 1, 8));
        }

        List<List<Object>> list = new ArrayList<>();
        if (seances != null) {
            seances.forEach(entry -> list.add(Arrays.asList(entry.getKey(),
                    entry.getValue())));
        }
        return list;
    }

    @GetMapping({"/home/page/count", "/page/count"})
    @ResponseBody
    public int getSeancesPagesCount(@RequestParam String cinemaId,
                                    @RequestParam(required = false) String genres,
                                    @RequestParam(required = false) String technologies) {
        Page<Map.Entry> seances;
        if (cinemaId == null) {
            cinemaId = cinemaService.getCinemaByLocation().getId();
        }
        if (genres != null && technologies != null) {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
                    Arrays.asList(genres.split(",").clone()),
                    PageRequest.of(0, 8));
        } else if (genres != null) {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), null,
                    Arrays.asList(genres.split(",").clone()),
                    PageRequest.of(0, 8));
        } else if (technologies != null) {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
                    null, PageRequest.of(0, 8));
        } else {
            seances = seanceService.findSeancesList(LocalDate.now(),
                    cinemaService.findById(cinemaId), null,
                    null, PageRequest.of(0, 8));
        }
        return seances.getTotalPages();
    }

//    private Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
//            List<SeanceForSeancesListDTO>>>> getSeanceMap (Page<Map.Entry> map) {
//        Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
//                List<SeanceForSeancesListDTO>>>> result = new HashMap<>();
//        map.forEach(entry -> result.put((MovieForSeanceDTO) entry.getKey(), (Map<LocalDate, Map<HallForSeanceDTO,
//                List<SeanceForSeancesListDTO>>>) entry.getValue()));
//        return result;
//    }
}
