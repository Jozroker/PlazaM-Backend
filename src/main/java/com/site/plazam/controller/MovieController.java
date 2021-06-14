package com.site.plazam.controller;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.Role;
import com.site.plazam.domain.Technology;
import com.site.plazam.dto.*;
import com.site.plazam.dto.comparator.MoviesByExpectedComparator;
import com.site.plazam.dto.comparator.MoviesByNewestComparator;
import com.site.plazam.dto.comparator.MoviesByPopularComparator;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.service.*;
import org.json.JSONObject;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    private final UserService userService;

    private final MovieService movieService;

    private final MessageSource messageSource;

    private final SeanceService seanceService;

    private final RatingService ratingService;

    private final CommentService commentService;

    private final TicketService ticketService;

    public MovieController(UserService userService,
                           MovieService movieService,
                           MessageSource messageSource,
                           SeanceService seanceService,
                           RatingService ratingService,
                           CommentService commentService,
                           TicketService ticketService) {
        this.userService = userService;
        this.movieService = movieService;
        this.messageSource = messageSource;
        this.seanceService = seanceService;
        this.ratingService = ratingService;
        this.commentService = commentService;
        this.ticketService = ticketService;
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
        return Arrays.stream(Technology.values())
                .map(Technology::name)
                .collect(Collectors.toList());
    }

    @PostMapping("/movie/{id}/add/rating")
    @ResponseBody
    @Transactional
    public String createRating(@PathVariable String id,
                               @RequestParam int rating,
                               Principal principal) {
        RatingCreateDTO ratingCreateDTO = new RatingCreateDTO();
        ratingCreateDTO.setMovie(movieService.findMovieFullById(id));
        ratingCreateDTO.setUser(userService.findByUsernameOrEmail(principal.getName(), principal.getName()));
        ratingCreateDTO.setUserRating(rating);
        return ratingService.save(ratingCreateDTO).getId();
    }

    @PostMapping("/movie/{id}/add/comment")
    @ResponseBody
    @Transactional
    public String createComment(@PathVariable String id,
                                @RequestParam(required = false) String ratingId,
                                @RequestParam String text,
                                Principal principal) {
        UserForSelfInfoDTO user =
                userService.findByUsernameOrEmail(principal.getName(), principal.getName());
        CommentCreateDTO comment = new CommentCreateDTO();
        comment.setUser(user);
        comment.setText(text);
        comment.setMovie(movieService.findMovieFullById(id));
        comment.setDate(LocalDate.now());
        comment.setTime(LocalDateTime.now());
        if (ratingId != null) {
            comment.setRating(ratingService.findById(ratingId));
        }
        return new JSONObject(commentService.save(comment)).toString();
    }

    @GetMapping("/coming-soon")
    @ResponseBody
    @Transactional
    public List<MovieForComingSoonDTO> getComingSoonMovies() {
        return movieService.findMovieForComingSoonByReleaseDateAfterOrderByReleaseDate(LocalDate.now());
    }

//    @GetMapping("/seance/{id}")
//    @Transactional
//    @ResponseBody
//    public String seance(@PathVariable String id) {
//        return new JSONObject(seanceService.findSeanceForTicketById(id)).toString();
//    }

    @GetMapping("/movie/{id}")
    @Transactional
    public String movie(ModelMap model, @PathVariable String id,
                        @RequestParam String cinemaId,
                        @RequestParam(required = false) String seanceId,
                        Principal principal) {
        MovieFullDTO movie = movieService.findMovieFullById(id);
        if (principal != null) {
            UserForSelfInfoDTO user = userService.findByUsernameOrEmail(principal.getName(),
                    principal.getName());
            RatingSimpleDTO rating = ratingService.findByUserAndMovie(user, movie);
//            model.addAttribute("favouriteMovies",
//                    user.getFavouriteMovieIds());
            model.addAttribute("userRating", rating);
            model.addAttribute("isFavourite",
                    user.getFavouriteMovieIds().contains(movie.getId()));
        }
        if (seanceId != null) {
            model.addAttribute("seanceId", seanceId);
        }
        model.addAttribute("movie", movie);
        model.addAttribute("comments",
                commentService.findByMovie(movie).stream()
                        .filter(comment -> !comment.isReported())
                        .collect(Collectors.toList()));
        //todo create pageable comments
        List<SeanceForSeancesListDTO> seances =
                seanceService.findByMovie(movieService.findMovieForSeanceById(id))
                        .stream()
                        .filter(seance -> seance.getHall().getCinemaId().equals(cinemaId) &&
                                (seance.getDateFrom().isBefore(LocalDate.now()) ||
                                        seance.getDateFrom().isEqual(LocalDate.now())))
                        .collect(Collectors.toList());
        if (seances.isEmpty()) {
            return "movie";
        }
        return "movie_in_route";
    }

    @GetMapping("/movies")
    @Transactional
    public String movies(ModelMap model, Principal principal,
                         @RequestParam(required = false) String userId,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) String page) {
        int pageCount = 25;
        int currentPage = page == null ? 0 : Integer.parseInt(page) - 1;
        if (principal != null) {
            UserForSelfInfoDTO user =
                    userService.findByUsernameOrEmail(principal.getName(), principal.getName());
            if (user.getRole() == Role.ADMIN) {
                pageCount = 24;
            }
        }
        List<MovieForMoviesListDTO> moviesList =
                movieService.findMovieForMoviesListAll();
        if (userId == null) {
            moviesList =
                    moviesList.stream().sorted(new MoviesByNewestComparator()).collect(Collectors.toList());
        } else {
            switch (type) {
                case "favourites":
                    moviesList = moviesList.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getFavouriteMovieIds()
                            .contains(movie.getId())).sorted(new MoviesByNewestComparator()).collect(Collectors.toList());
                    break;
                case "waited":
                    moviesList = moviesList.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getWaitMovieIds()
                            .contains(movie.getId())).sorted(new MoviesByNewestComparator()).collect(Collectors.toList());
                    break;
                case "viewed":
                    moviesList = moviesList.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getViewedMovieIds()
                            .contains(movie.getId())).sorted(new MoviesByNewestComparator()).collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }
        PagedListHolder moviesPage =
                new PagedListHolder(moviesList);
        moviesPage.setPage(currentPage);
        moviesPage.setPageSize(pageCount);
        if (currentPage > moviesPage.getPageCount() - 1) {
            return "redirect:/error";
        }
        if (principal != null) {
            model.addAttribute("favouriteMovies",
                    userService.findByUsernameOrEmail(principal.getName(),
                            principal.getName()).getFavouriteMovieIds());
        }
        model.addAttribute("pagesCount", moviesPage.getPageCount());
        model.addAttribute("movies", moviesPage.getPageList());
        //todo change json library to jackson
        return "movies";
    }

    @GetMapping("/seance/{id}")
    @Transactional
    public String seanceTickets(@PathVariable String id, ModelMap model,
                                @RequestParam String date) {
        model.addAttribute("seance", seanceService.findSeanceForTicketById(id));
        model.addAttribute("selectedDate", LocalDate.parse(date));
        return "buy-ticket";
    }

    @GetMapping("/seance/{id}/tickets")
    @ResponseBody
    @Transactional
    public String seanceTickets(@PathVariable String id,
                                @RequestParam String date) {
        LocalDate selectedDate = LocalDate.parse(date);
        List<TicketSimpleDTO> tickets =
                ticketService.findBySeance(seanceService.findSeanceForTicketById(id))
                        .stream().filter(ticket -> ticket.getDate().getDayOfMonth() == selectedDate.getDayOfMonth() &&
                        ticket.getDate().getMonthValue() == selectedDate.getMonthValue() &&
                        ticket.getDate().getYear() == selectedDate.getYear())
                        .collect(Collectors.toList());
        Map<Integer, List<Integer>> ticketsMap = new HashMap<>();
        for (TicketSimpleDTO ticket : tickets) {
            if (!ticketsMap.containsKey(ticket.getPlaceRow())) {
                ticketsMap.put(ticket.getPlaceRow(), new ArrayList<>());
            }
            ticketsMap.get(ticket.getPlaceRow()).add(ticket.getPlaceSeat());
        }
        return new JSONObject(ticketsMap).toString();
    }

    @GetMapping("/movies/{page}")
    @ResponseBody
    @Transactional
    public List<Object> movies(@PathVariable int page, Principal principal,
                               @RequestParam(required = false) String sort,
                               @RequestParam(required = false) String yearFrom,
                               @RequestParam(required = false) String yearTo,
                               @RequestParam(required = false) String genres,
                               @RequestParam(required = false) String userId,
                               @RequestParam(required = false) String type) {
        Page<MovieForMoviesListDTO> movies;
        int pageCount = 25;
        if (principal != null) {
            UserForSelfInfoDTO user =
                    userService.findByUsernameOrEmail(principal.getName(), principal.getName());
            if (user.getRole() == Role.ADMIN) {
                pageCount = 24;
            }
        }
        if (yearFrom != null && yearTo != null) {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBetween(LocalDate.of(Integer.parseInt(yearFrom), 1, 1),
                                LocalDate.of(Integer.parseInt(yearTo), 1, 1),
                                PageRequest.of(0, Integer.MAX_VALUE));
            } else {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBetweenAndGenresMatches(LocalDate.of(Integer.parseInt(yearFrom), 1, 1),
                                LocalDate.of(Integer.parseInt(yearTo), 1, 1),
                                Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                                PageRequest.of(0, Integer.MAX_VALUE));
            }
        } else if (yearFrom != null) {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateAfter(LocalDate.of(Integer.parseInt(yearFrom), 1, 1), PageRequest.of(0, Integer.MAX_VALUE));
            } else {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateAfterAndGenresMatches(LocalDate.of(Integer.parseInt(yearFrom), 1, 1),
                                Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                                PageRequest.of(0, Integer.MAX_VALUE));
            }
        } else if (yearTo != null) {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBefore(LocalDate.of(Integer.parseInt(yearTo), 1, 1), PageRequest.of(0, Integer.MAX_VALUE));
            } else {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBeforeAndGenresMatches(LocalDate.of(Integer.parseInt(yearTo), 1, 1),
                                Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                                PageRequest.of(0, Integer.MAX_VALUE));
            }
        } else {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListAll(PageRequest.of(0, Integer.MAX_VALUE));
            } else {
                movies = movieService.findAllByGenresMatches(
                        Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                        PageRequest.of(0, Integer.MAX_VALUE));
            }

        }
        if (sort != null) {
            switch (sort) {
                case "newest":
                    movies =
                            new PageImpl<>(movies.stream().sorted(new MoviesByNewestComparator()).collect(Collectors.toList()),
                                    PageRequest.of(0, Integer.MAX_VALUE),
                                    movies.getTotalElements());
                    break;
                case "popular":
                    movies =
                            new PageImpl<>(movies.stream().sorted(new MoviesByPopularComparator()).collect(Collectors.toList()),
                                    PageRequest.of(0, Integer.MAX_VALUE),
                                    movies.getTotalElements());
                    break;
                case "expected":
                    movies =
                            new PageImpl<>(movies.stream().sorted(new MoviesByExpectedComparator()).collect(Collectors.toList()),
                                    PageRequest.of(0, Integer.MAX_VALUE),
                                    movies.getTotalElements());
                    break;
                default:
                    break;
            }
        }
        if (userId != null) {
            switch (type) {
                case "favourites":
                    movies =
                            new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getFavouriteMovieIds()
                                    .contains(movie.getId())).collect(Collectors.toList()), PageRequest.of(0, Integer.MAX_VALUE), movies.getTotalElements());
                    break;
                case "waited":
                    movies =
                            new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getWaitMovieIds()
                                    .contains(movie.getId())).collect(Collectors.toList()), PageRequest.of(0, Integer.MAX_VALUE), movies.getTotalElements());
                    break;
                case "viewed":
                    movies =
                            new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getViewedMovieIds()
                                    .contains(movie.getId())).collect(Collectors.toList()), PageRequest.of(0, Integer.MAX_VALUE), movies.getTotalElements());
                    break;
                default:
                    break;
            }
        }
        PagedListHolder moviesPage = new PagedListHolder(movies.getContent());
        moviesPage.setPageSize(pageCount);
        moviesPage.setPage(page - 1);
        List<Object> list = new ArrayList<>();
        list.add(moviesPage.getPageCount());
        list.addAll(moviesPage.getPageList());
        return list;
    }
}
