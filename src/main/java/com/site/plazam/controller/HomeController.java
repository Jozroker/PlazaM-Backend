package com.site.plazam.controller;

import com.site.plazam.domain.Lang;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.MovieForHomeSliderDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.comparator.CinemaByCityComparator;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.service.*;
import org.json.JSONObject;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final CinemaService cinemaService;

    private final UserService userService;

    private final SeanceService seanceService;

    private final HallService hallService;

    private final MovieService movieService;

    public HomeController(CinemaService cinemaService,
                          UserService userService,
                          SeanceService seanceService,
                          HallService hallService,
                          MovieService movieService) {
        this.cinemaService = cinemaService;
        this.userService = userService;
        this.seanceService = seanceService;
        this.hallService = hallService;
        this.movieService = movieService;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
    }

    @GetMapping({"/home", "/"})
    @Transactional
    public String home(ModelMap model) {
//        CinemaDTO cinema = cinemaService.getCinemaByLocation();
//        if (principal != null) {
//            UserForSelfInfoDTO user =
//                    userService.findByUsernameOrEmail(principal.getName(), principal.getName());
//            if (user != null) {
//                cinema = user.getSelectedCinema();
//            }
//        }
//        Page<Map.Entry> seances = seanceService.findSeancesList(LocalDate.now(),
//                cinemaService.findById(cinema.getId()), null, null,
//                false, PageRequest.of(0, 8));
        List<MovieForHomeSliderDTO> homeSliderMovies =
                movieService.findMovieForHomeSliderByReleaseDateBeforeOrderByReleaseDate(LocalDate.now());
//        model.addAttribute("pagesCount", seances.getTotalPages());
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
    public String header(ModelMap model,
                         Principal principal) {
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

    @GetMapping("/schedule")
    @Transactional
    public String schedule(ModelMap model, Principal principal,
                           @RequestParam(required = false) String page) {
        int currentPage = page == null ? 0 : Integer.parseInt(page) - 1;
        CinemaDTO cinema = cinemaService.getCinemaByLocation();
        if (principal != null) {
            UserForSelfInfoDTO user =
                    userService.findByUsernameOrEmail(principal.getName(), principal.getName());
            if (user != null) {
                cinema = user.getSelectedCinema();
            }
        }
        PagedListHolder<Map.Entry> seances =
                seanceService.findSeancesList(LocalDate.now(),
                        cinemaService.findById(cinema.getId()), null, null,
                        true, null, PageRequest.of(currentPage, 8));
        if (currentPage > seances.getPageCount() - 1) {
            return "redirect:/error";
        }
        model.addAttribute("pagesCount", seances.getPageCount());
        model.addAttribute("currentDate", LocalDate.now());
        return "schedule";
    }

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

    @GetMapping("/movie/{id}/seances")
    @ResponseBody
    @Transactional
    public List<List<Object>> movieSeances(@PathVariable String id,
                                           @RequestParam String cinemaId) {
        List<List<Object>> list = new ArrayList<>();
        seanceService.findSeancesList(LocalDate.now(),
                cinemaService.findById(cinemaId), null, null, false,
                movieService.findMovieForSeanceById(id),
                PageRequest.of(0, 1)).getPageList()
                .forEach(entry -> list.add(Arrays.asList(entry.getKey(), entry.getValue())));
        return list;
    }

    @GetMapping("/cinemas/schedule-creation")
    @ResponseBody
    @Transactional
    public List<CinemaDTO> getCinemasScheduleCreation() {
        return cinemaService.findAll().stream().sorted(new CinemaByCityComparator()).collect(Collectors.toList());
    }

    @GetMapping({"/home/page/{page}", "/page/{page}", "/schedule/page/{page}"})
    @ResponseBody
    @Transactional
    public List<List<Object>> getSeancesPage(@PathVariable int page,
                                             @RequestParam String cinemaId,
                                             @RequestParam(required = false) String genres,
                                             @RequestParam(required = false) String technologies,
                                             @RequestParam(required = false) String date) {
        PagedListHolder<Map.Entry> seances;
        boolean singleDate = false;
        LocalDate selectedDate = LocalDate.now();
        if (cinemaId == null) {
            cinemaId = cinemaService.getCinemaByLocation().getId();
        }
        if (date != null) {
            singleDate = true;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            selectedDate = LocalDate.parse(date, formatter);
        }
        if (genres != null && technologies != null) {
            seances = seanceService.findSeancesList(selectedDate,
                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
                    Arrays.asList(genres.split(",").clone()),
                    singleDate, null, PageRequest.of(page - 1, 8));
        } else if (genres != null) {
            seances = seanceService.findSeancesList(selectedDate,
                    cinemaService.findById(cinemaId), null,
                    Arrays.asList(genres.split(",").clone()),
                    singleDate, null, PageRequest.of(page - 1, 8));
        } else if (technologies != null) {
            seances = seanceService.findSeancesList(selectedDate,
                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
                    null, singleDate, null, PageRequest.of(page - 1, 8));
        } else {
            seances = seanceService.findSeancesList(selectedDate,
                    cinemaService.findById(cinemaId), null,
                    null, singleDate, null, PageRequest.of(page - 1, 8));
        }

        List<List<Object>> list = new ArrayList<>();
        if (seances != null) {
            list.add(Collections.singletonList(seances.getPageCount()));
            seances.getPageList().forEach(entry -> list.add(Arrays.asList(entry.getKey(),
                    entry.getValue())));
        }
        return list;
        //todo change list to json object
    }

//    @GetMapping({"/page/count", "/home/page/count", "/schedule/page/count"})
//    @ResponseBody
//    @Transactional
//    public int getSeancesPagesCount(@RequestParam String cinemaId,
//                                    @RequestParam(required = false) String genres,
//                                    @RequestParam(required = false) String technologies,
//                                    @RequestParam(required = false) String date) {
//        Page<Map.Entry> seances;
//        boolean singleDate = false;
//        LocalDate selectedDate = LocalDate.now();
//        if (cinemaId == null) {
//            cinemaId = cinemaService.getCinemaByLocation().getId();
//        }
//        if (date != null) {
//            singleDate = true;
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
//            selectedDate = LocalDate.parse(date, formatter);
//        }
//        if (genres != null && technologies != null) {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
//                    Arrays.asList(genres.split(",").clone()),
//                    singleDate, PageRequest.of(0, 8));
//        } else if (genres != null) {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), null,
//                    Arrays.asList(genres.split(",").clone()),
//                    singleDate, PageRequest.of(0, 8));
//        } else if (technologies != null) {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
//                    null, singleDate, PageRequest.of(0, 8));
//        } else {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), null,
//                    null, singleDate, PageRequest.of(0, 8));
//        }
//        return seances.getTotalPages();
//    }

//    @GetMapping("/schedule/page/count")
//    @ResponseBody
//    public int getScheduleSeancesPagesCount(@RequestParam String cinemaId,
//                                    @RequestParam(required = false) String genres,
//                                    @RequestParam(required = false) String technologies,
//                                    @RequestParam(required = false) String date) {
//        Page<Map.Entry> seances;
//        LocalDate selectedDate = LocalDate.now();
//        CinemaDTO cinema = cinemaService.getCinemaByLocation();
//        if (cinemaId != null) {
//            cinema = cinemaService.findById(cinemaId);
//        }
//        List<HallForSeanceDTO> halls =
//                hallService.findHallForSeanceByCinema(cinema);
//        if (date != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
//            selectedDate = LocalDate.parse(date, formatter);
//        }
//        if (genres != null && technologies != null) {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
//                    Arrays.asList(genres.split(",").clone()),
//                    PageRequest.of(0, 8));
//            seances =
//                    seanceService.findByDateFromBeforeEqualsAndDateToAfterEqualsAndHalls(selectedDate, selectedDate, halls, PageRequest.)
//        } else if (genres != null) {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), null,
//                    Arrays.asList(genres.split(",").clone()),
//                    PageRequest.of(0, 8));
//        } else if (technologies != null) {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), Arrays.asList(technologies.split(",").clone()),
//                    null, PageRequest.of(0, 8));
//        } else {
//            seances = seanceService.findSeancesList(selectedDate,
//                    cinemaService.findById(cinemaId), null,
//                    null, PageRequest.of(0, 8));
//        }
//        return seances.getTotalPages();
//    }

//    private Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
//            List<SeanceForSeancesListDTO>>>> getSeanceMap (Page<Map.Entry> map) {
//        Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
//                List<SeanceForSeancesListDTO>>>> result = new HashMap<>();
//        map.forEach(entry -> result.put((MovieForSeanceDTO) entry.getKey(), (Map<LocalDate, Map<HallForSeanceDTO,
//                List<SeanceForSeancesListDTO>>>) entry.getValue()));
//        return result;
//    }
}
