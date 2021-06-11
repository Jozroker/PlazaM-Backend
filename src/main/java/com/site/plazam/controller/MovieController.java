package com.site.plazam.controller;

import com.site.plazam.domain.*;
import com.site.plazam.dto.*;
import com.site.plazam.dto.comparator.CinemaByCityComparator;
import com.site.plazam.dto.comparator.MoviesByExpectedComparator;
import com.site.plazam.dto.comparator.MoviesByNewestComparator;
import com.site.plazam.dto.comparator.MoviesByPopularComparator;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.error.TimeAlreadyScheduledException;
import com.site.plazam.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    private final CinemaService cinemaService;

    private final UserService userService;

    private final ActorService actorService;

    private final MovieService movieService;

    private final MessageSource messageSource;

    private final SeanceService seanceService;

    private final RatingService ratingService;

    private final CommentService commentService;

    private final HallService hallService;

    private final TicketService ticketService;

    public MovieController(CinemaService cinemaService,
                           UserService userService,
                           ActorService actorService,
                           MovieService movieService,
                           MessageSource messageSource,
                           SeanceService seanceService,
                           RatingService ratingService,
                           CommentService commentService,
                           HallService hallService,
                           TicketService ticketService) {
        this.cinemaService = cinemaService;
        this.userService = userService;
        this.actorService = actorService;
        this.movieService = movieService;
        this.messageSource = messageSource;
        this.seanceService = seanceService;
        this.ratingService = ratingService;
        this.commentService = commentService;
        this.hallService = hallService;
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
        List<String> techs = new ArrayList<>();
        techs = Arrays.stream(Technology.values()).map(Technology::getType).collect(Collectors.toList());
        return techs;
    }

    @GetMapping("/admin/movie/schedule-creation")
    public String getScheduleCreation(@RequestParam(required = false) String seanceId, ModelMap model) {
        if (seanceId != null) {
            model.addAttribute("seance",
                    seanceService.findSeanceForTicketById(seanceId));
        }
        return "schedule_creation";
    }

    @GetMapping("/cinemas/schedule-creation")
    @ResponseBody
    @Transactional
    public List<CinemaDTO> getCinemasScheduleCreation() {
        return cinemaService.findAll().stream().sorted(new CinemaByCityComparator()).collect(Collectors.toList());
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

    @GetMapping("/admin/movie/create")
    public String createMovie(ModelMap model) {
        model.addAttribute("movie", new MovieCreateDTO());
        model.addAttribute("actors", actorService.findAll());
        return "create_change_movie";
    }

    @GetMapping("/admin/movie/change/{movieId}")
    public String createMovie(@PathVariable String movieId, ModelMap model) {
        model.addAttribute("movie", movieService.findMovieCreateById(movieId));
        model.addAttribute("actors", actorService.findAll());
        return "create_change_movie";
    }

    @PostMapping(value = {"/admin/movie/create", "/admin/movie/change/{movieId}"}, consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public String createMovie(@PathVariable(required = false) String movieId,
                              @RequestBody String movie) {
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO();
        if (movieId != null) {
            movieCreateDTO = movieService.findMovieCreateById(movieId);
        }
        JSONObject obj = new JSONObject(movie);
        movieCreateDTO.setName(new HashMap<String, String>() {{
            put("eng", obj.getString("nameEng"));
            put("ukr", obj.getString("nameUkr"));
            put("pol", obj.getString("namePol"));
        }});
        movieCreateDTO.setSurname(new HashMap<String, String>() {{
            put("eng", obj.getString("surnameEng"));
            put("ukr", obj.getString("surnameUkr"));
            put("pol", obj.getString("surnamePol"));
        }});
        movieCreateDTO.setFullName(new HashMap<String, String>() {{
            put("eng", obj.getString("nameEng") + " " + obj.getString(
                    "surnameEng"));
            put("ukr", obj.getString("nameUkr") + " " + obj.getString(
                    "surnameUkr"));
            put("pol", obj.getString("namePol") + " " + obj.getString(
                    "surnamePol"));
        }});
        movieCreateDTO.setDuration(obj.getInt("duration"));
        movieCreateDTO.setDirectedBy(new HashMap<String, String>() {{
            put("eng", obj.getString("directedByEng"));
            put("ukr", obj.getString("directedByUkr"));
            put("pol", obj.getString("directedByPol"));
        }});
        movieCreateDTO.setReleaseDate(LocalDate.parse(obj.getString(
                "releaseDate")));
        movieCreateDTO.setMovieCountry(new HashMap<String, String>() {{
            put("eng", obj.getString("countryEng"));
            put("ukr", obj.getString("countryUkr"));
            put("pol", obj.getString("countryPol"));
        }});
        movieCreateDTO.setMovieLang(Lang.valueOf(obj.getString("movieLang")));
        movieCreateDTO.setMpaaRating(MPAA.valueOf(obj.getString("mpaa")));
        movieCreateDTO.setImdbRating(obj.getDouble("imdb"));
        movieCreateDTO.setDescription(new HashMap<String, String>() {{
            put("eng", obj.getString("descEng"));
            put("ukr", obj.getString("descUkr"));
            put("pol", obj.getString("descPol"));
        }});
        if (obj.has("movieWidePicture")) {
            PictureDTO widePicture = new PictureDTO();
            widePicture.setFormat(obj.getString("movieWidePictureFormat"));
            widePicture.setPicture(Base64.getDecoder().decode(obj.getString(
                    "movieWidePicture")));
            movieCreateDTO.setWidePicture(widePicture);
        }
        if (obj.has("moviePosterPicture")) {
            PictureDTO posterPicture = new PictureDTO();
            posterPicture.setFormat(obj.getString("moviePosterPictureFormat"));
            posterPicture.setPicture(Base64.getDecoder().decode(obj.getString(
                    "moviePosterPicture")));
            movieCreateDTO.setPosterPicture(posterPicture);
        }
        if (!obj.getJSONArray("galleryPictures").isNull(0)) {
            List<PictureDTO> pictures = new ArrayList<>();
            PictureDTO picture;
            for (int i = 0; i < obj.getJSONArray("galleryPictures").length(); i++) {
                picture = new PictureDTO();
                picture.setFormat(obj.getJSONArray("galleryPicturesFormats").getString(i));
                picture.setPicture(Base64.getDecoder().decode(obj.getJSONArray("galleryPictures").getString(i)));
                pictures.add(picture);
            }
            movieCreateDTO.setGalleryPictures(pictures);
        }
        List<Genre> genres = new ArrayList<>();
        List<ActorForActorListDTO> actors = new ArrayList<>();
        for (int i = 0; i < obj.getJSONArray("selectedGenres").length(); i++) {
            genres.add(Genre.valueOf(obj.getJSONArray("selectedGenres").getString(i)));
        }
        for (int i = 0; i < obj.getJSONArray("selectedActors").length(); i++) {
            actors.add(actorService.findById(obj.getJSONArray("selectedActors").getString(i)));
        }
        movieCreateDTO.setGenres(genres);
        movieCreateDTO.setActors(actors);
        movieService.save(movieCreateDTO);
        return "redirect:/movies";
    }

    @PostMapping(value = "/admin/create/actor", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional
    public String createActor(@RequestBody String actor) {
        ActorCreateDTO actorCreateDTO = new ActorCreateDTO();
        JSONObject object = new JSONObject(actor);
        actorCreateDTO.setFirstName(new HashMap<String, String>() {{
            put("eng", object.getString("firstNameEng"));
            put("ukr", object.getString("firstNameUkr"));
            put("pol", object.getString("firstNamePol"));
        }});
        actorCreateDTO.setLastName(new HashMap<String, String>() {{
            put("eng", object.getString("lastNameEng"));
            put("ukr", object.getString("lastNameUkr"));
            put("pol", object.getString("lastNamePol"));
        }});
        if (object.has("picture")) {
            PictureDTO picture = new PictureDTO();
            picture.setFormat(object.getString("pictureFormat"));
            picture.setPicture(Base64.getDecoder().decode(object.getString(
                    "picture")));
            actorCreateDTO.setPicture(picture);
        }
        return new JSONObject(actorService.save(actorCreateDTO)).toString();
    }

    @PostMapping("/admin/schedule/create")
    @ResponseBody
    @Transactional
    public String createSchedule(@RequestParam String movieId,
                                 @RequestParam String hallId,
                                 @RequestParam String dateFrom,
                                 @RequestParam String dateTo,
                                 @RequestParam String startTime,
                                 @RequestParam String price,
                                 @RequestParam String days,
                                 @RequestParam(required = false) String seanceId) {
        //todo change services that get requests have object in object, but
        // post requests - 1 object with ids
        try {
            SeanceCreateDTO seanceCreateDTO = new SeanceCreateDTO();
            if (seanceId != null) {
                seanceCreateDTO.setId(seanceId);
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy HH:mm");
            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movieId));
            seanceCreateDTO.setHall(hallService.findHallForTicketById(hallId));
            seanceCreateDTO.setDateFrom(LocalDate.parse(dateFrom, dateFormatter));
            seanceCreateDTO.setDateTo(LocalDate.parse(dateTo, dateFormatter));
            seanceCreateDTO.setStartSeance(LocalDateTime.parse("01.01.0001 " + startTime,
                    timeFormatter));
            seanceCreateDTO.setEndSeance(LocalDateTime.parse("01.01.0001 " + startTime,
                    timeFormatter).plusMinutes(movieService.findMovieForSeanceById(movieId).getDurationInMinutes()));
            seanceCreateDTO.setTicketPrice(Double.parseDouble(price));
            seanceCreateDTO.setDays(Arrays.stream(days.split(",")).map(Day::valueOf).collect(Collectors.toList()));
            seanceService.save(seanceCreateDTO);
        } catch (TimeAlreadyScheduledException exception) {
            return "failed";
        }
        return "success";
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
        if (seanceService.findByMovie(movieService.findMovieForSeanceById(id)).isEmpty()) {
            return "movie";
        }
        return "movie_in_route";
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
                PageRequest.of(0, 1)).forEach(entry -> list.add(Arrays.asList(entry.getKey(), entry.getValue())));
        return list;
    }

    @PostMapping(value = "/ticket/buy", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public String buyTicket(@RequestBody String data, Principal principal) {
        UserForSelfInfoDTO user =
                userService.findByUsernameOrEmail(principal.getName(), principal.getName());
        JSONObject object = new JSONObject(data);
        JSONArray array = object.getJSONArray("tickets");
        TicketSimpleDTO ticket;
        for (int i = 0; i < array.length(); i++) {
            ticket = new TicketSimpleDTO();
            ticket.setDate(LocalDate.parse(array.getJSONObject(i).getString(
                    "date")));
            if (user.getRole() == Role.WORKER) {
                ticket.setPaymentStatus(true);
            } else {
                ticket.setPaymentStatus(array.getJSONObject(i).getBoolean(
                        "paymentStatus"));
            }
            ticket.setPlaceRow(array.getJSONObject(i).getInt("row"));
            ticket.setPlaceSeat(array.getJSONObject(i).getInt("seat"));
            ticket.setSeance(seanceService.findSeanceForTicketById(array
                    .getJSONObject(i).getString("seanceId")));
            user.getTickets().add(ticketService.save(ticket));
        }
        userService.updateLists(user);
        return "redirect:/movie/" + object.getString("movieId");
    }

    @GetMapping("/movies")
    @Transactional
    public String movies(ModelMap model, Principal principal,
                         @RequestParam(required = false) String userId,
                         @RequestParam(required = false) String type) {
        int pageCount = 25;
        if (principal != null) {
            UserForSelfInfoDTO user =
                    userService.findByUsernameOrEmail(principal.getName(), principal.getName());
            if (user.getRole() == Role.ADMIN) {
                pageCount = 24;
            }
        }
        Page<MovieForMoviesListDTO> movies = movieService.findMovieForMoviesListAll(PageRequest.of(0, pageCount));
        if (userId == null) {
            movies =
                    new PageImpl<>(movies.stream().sorted(new MoviesByNewestComparator()).collect(Collectors.toList()),
                            PageRequest.of(0, pageCount), movies.getTotalElements());
        } else {
            switch (type) {
                case "favourites":
                    movies = new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getFavouriteMovieIds()
                            .contains(movie.getId())).sorted(new MoviesByNewestComparator()).collect(Collectors.toList()),
                            PageRequest.of(0, pageCount), movies.getTotalElements());
                    break;
                case "waited":
                    movies = new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getWaitMovieIds()
                            .contains(movie.getId())).sorted(new MoviesByNewestComparator()).collect(Collectors.toList()),
                            PageRequest.of(0, pageCount), movies.getTotalElements());
                    break;
                case "viewed":
                    movies = new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getViewedMovieIds()
                            .contains(movie.getId())).sorted(new MoviesByNewestComparator()).collect(Collectors.toList()),
                            PageRequest.of(0, pageCount), movies.getTotalElements());
                    break;
                default:
                    break;
            }
        }
        if (principal != null) {
            model.addAttribute("favouriteMovies",
                    userService.findByUsernameOrEmail(principal.getName(),
                            principal.getName()).getFavouriteMovieIds());
        }
        model.addAttribute("pagesCount", movies.getTotalPages());
        model.addAttribute("movies", movies.getContent());
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
                                PageRequest.of(page - 1, pageCount));
            } else {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBetweenAndGenresMatches(LocalDate.of(Integer.parseInt(yearFrom), 1, 1),
                                LocalDate.of(Integer.parseInt(yearTo), 1, 1),
                                Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                                PageRequest.of(page - 1, pageCount));
            }
        } else if (yearFrom != null) {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateAfter(LocalDate.of(Integer.parseInt(yearFrom), 1, 1), PageRequest.of(page - 1, pageCount));
            } else {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateAfterAndGenresMatches(LocalDate.of(Integer.parseInt(yearFrom), 1, 1),
                                Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                                PageRequest.of(page - 1, pageCount));
            }
        } else if (yearTo != null) {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBefore(LocalDate.of(Integer.parseInt(yearTo), 1, 1), PageRequest.of(page - 1, pageCount));
            } else {
                movies =
                        movieService.findMovieForMoviesListByReleaseDateBeforeAndGenresMatches(LocalDate.of(Integer.parseInt(yearTo), 1, 1),
                                Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                                PageRequest.of(page - 1, pageCount));
            }
        } else {
            if (genres == null) {
                movies =
                        movieService.findMovieForMoviesListAll(PageRequest.of(page - 1, pageCount));
            } else {
                movies = movieService.findAllByGenresMatches(
                        Arrays.stream(genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()),
                        PageRequest.of(page - 1, pageCount));
            }

        }
        if (sort != null) {
            switch (sort) {
                case "newest":
                    movies =
                            new PageImpl<>(movies.stream().sorted(new MoviesByNewestComparator()).collect(Collectors.toList()),
                                    PageRequest.of(page - 1, pageCount), movies.getTotalElements());
                    break;
                case "popular":
                    movies =
                            new PageImpl<>(movies.stream().sorted(new MoviesByPopularComparator()).collect(Collectors.toList()),
                                    PageRequest.of(page - 1, pageCount), movies.getTotalElements());
                    break;
                case "expected":
                    movies =
                            new PageImpl<>(movies.stream().sorted(new MoviesByExpectedComparator()).collect(Collectors.toList()),
                                    PageRequest.of(page - 1, pageCount), movies.getTotalElements());
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
                                    .contains(movie.getId())).collect(Collectors.toList()), PageRequest.of(page - 1, pageCount), movies.getTotalElements());
                    break;
                case "waited":
                    movies =
                            new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getWaitMovieIds()
                                    .contains(movie.getId())).collect(Collectors.toList()), PageRequest.of(page - 1, pageCount), movies.getTotalElements());
                    break;
                case "viewed":
                    movies =
                            new PageImpl<>(movies.stream().filter(movie -> userService.findUserForSelfInfoById(userId).getViewedMovieIds()
                                    .contains(movie.getId())).collect(Collectors.toList()), PageRequest.of(page - 1, pageCount), movies.getTotalElements());
                    break;
                default:
                    break;
            }
        }
        List<Object> list = new ArrayList<>();
        list.add(movies.getTotalPages());
        list.addAll(movies.getContent());
        return list;
    }
}
