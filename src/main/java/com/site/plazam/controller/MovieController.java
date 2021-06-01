package com.site.plazam.controller;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.Technology;
import com.site.plazam.dto.MovieForComingSoonDTO;
import com.site.plazam.dto.MovieForResultListDTO;
import com.site.plazam.dto.comparator.CinemaByCityComparator;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.service.CinemaService;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.SeanceService;
import com.site.plazam.service.UserService;
import org.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    private final CinemaService cinemaService;

    private final UserService userService;

    private final SeanceService seanceService;

    private final MovieService movieService;

    private final MessageSource messageSource;

    public MovieController(CinemaService cinemaService,
                           UserService userService,
                           SeanceService seanceService,
                           MovieService movieService,
                           MessageSource messageSource) {
        this.cinemaService = cinemaService;
        this.userService = userService;
        this.seanceService = seanceService;
        this.movieService = movieService;
        this.messageSource = messageSource;
    }

    @GetMapping("/movies/result")
    @ResponseBody
    public List<MovieForResultListDTO> getSeancesPage(@RequestParam String movieFullName) {
        return movieService.findMovieForResultListByMovieName(movieFullName);
    }

    @GetMapping("/genres")
    @ResponseBody
    public String getGenres() {
        Map<Genre, String> genres = new HashMap<>();
        MessageSourceAccessor msa = new MessageSourceAccessor(messageSource,
                LocaleContextHolder.getLocale());
        Arrays.stream(Genre.values()).forEach(genre -> genres.put(genre, msa.getMessage(
                "genre." + genre.name().replace("_", ".")
                        .toLowerCase())));
        return new JSONObject(genres).toString();
    }

    @GetMapping("/technologies")
    @ResponseBody
    public List<String> getTechnologies() {
        List<String> techs = new ArrayList<>();
        techs = Arrays.stream(Technology.values()).map(Technology::getType).collect(Collectors.toList());
        return techs;
    }

    @GetMapping("/admin/movie/schedule-creation")
    public String getScheduleCreation() {
        return "schedule_creation";
    }

    @GetMapping("/cinemas/schedule-creation")
    @ResponseBody
    @Transactional
    public List<CinemaDTO> getCinemasScheduleCreation() {
        return cinemaService.findAll().stream().sorted(new CinemaByCityComparator()).collect(Collectors.toList());
    }

    @GetMapping("/coming-soon")
    @ResponseBody
    @Transactional
    public List<MovieForComingSoonDTO> getComingSoonMovies() {
        return movieService.findMovieForComingSoonByReleaseDateAfterOrderByReleaseDate(LocalDate.now());
    }
}
