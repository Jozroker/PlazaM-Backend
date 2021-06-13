package com.site.plazam.controller;

import com.site.plazam.domain.*;
import com.site.plazam.dto.*;
import com.site.plazam.dto.comparator.UsersByRealNameComparator;
import com.site.plazam.dto.comparator.UsersByUsernameComparator;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.error.TimeAlreadyScheduledException;
import com.site.plazam.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final SeanceService seanceService;

    private final ActorService actorService;

    private final MovieService movieService;

    private final HallService hallService;

    private final UserService userService;

    private final CommentService commentService;

    public AdminController(SeanceService seanceService,
                           ActorService actorService,
                           MovieService movieService,
                           HallService hallService,
                           UserService userService,
                           CommentService commentService) {
        this.seanceService = seanceService;
        this.actorService = actorService;
        this.movieService = movieService;
        this.hallService = hallService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/admin/movie/schedule-creation")
    public String getScheduleCreation(@RequestParam(required = false) String seanceId, ModelMap model) {
        if (seanceId != null) {
            model.addAttribute("seance",
                    seanceService.findSeanceForTicketById(seanceId));
        }
        return "schedule_creation";
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

    @PostMapping(value = {"/admin/movie/create", "/admin/movie/change/{movieId}"},
            consumes =
                    {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional
    public String createMovie(@PathVariable(required = false) String movieId,
                              @RequestBody String movie) {
        //todo 1. find movie by name (eng or ukr or pol) and if movies is
        // equal throw exception BUT if only language is different - set
        // multilang trigger for both movies as true, and when movies is
        // generated in frontend view special rect block with language
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO();
        if (movieId != null) {
            movieCreateDTO = movieService.findMovieCreateById(movieId);
            movieCreateDTO.setGalleryPictures(new ArrayList<>());
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
//        movieCreateDTO.setMovieLang(Lang.valueOf(obj.getString("movieLang")));
        movieCreateDTO.setMpaaRating(MPAA.valueOf(obj.getString("mpaa")));
        movieCreateDTO.setDescription(new HashMap<String, String>() {{
            put("eng", obj.getString("descEng"));
            put("ukr", obj.getString("descUkr"));
            put("pol", obj.getString("descPol"));
        }});
        if (obj.has("imdb")) {
            if (!obj.getString("imdb").isEmpty()) {
                movieCreateDTO.setImdbRating(obj.getDouble("imdb"));
            }
        }
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
        } else {
            movieCreateDTO.setGalleryPictures(new ArrayList<>());
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
        return "/movies";
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
        if (object.has("picture") && !object.isNull("picture")) {
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
                                 @RequestParam String lang,
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
            seanceCreateDTO.setSeanceLang(Lang.valueOf(lang));
            seanceCreateDTO.setDays(Arrays.stream(days.split(",")).map(Day::valueOf).collect(Collectors.toList()));
            seanceService.save(seanceCreateDTO);
        } catch (TimeAlreadyScheduledException exception) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/admin/users")
    @Transactional
    public String users(ModelMap model,
                        @RequestParam(required = false) String page) {
        int currentPage = page == null ? 0 : Integer.parseInt(page) - 1;
        List<UserForUsersListDTO> allUsers =
                userService.findUserForUsersListAll();
        List<CommentForReportedListDTO> reportedUsers =
                commentService.findByReportedTrue();
        List<UserForBannedListDTO> bannedUsers =
                userService.findByBannedTrue();
        allUsers.sort(new UsersByUsernameComparator());
        reportedUsers.sort(new UsersByUsernameComparator());
        bannedUsers.sort(new UsersByUsernameComparator());
        Page<UserForUsersListDTO> allUsersPage = new PageImpl<>(allUsers,
                PageRequest.of(currentPage, 14), allUsers.size());
        Page<CommentForReportedListDTO> reportedUsersPage =
                new PageImpl<>(reportedUsers,
                        PageRequest.of(currentPage, 21), reportedUsers.size());
        Page<UserForBannedListDTO> bannedUsersPage =
                new PageImpl<>(bannedUsers,
                        PageRequest.of(currentPage, 14), bannedUsers.size());
        if (currentPage > 0 && allUsersPage.getContent().isEmpty()
                && reportedUsersPage.getContent().isEmpty() && bannedUsersPage.getContent().isEmpty()) {
            return "redirect:/error";
        }
        model.addAttribute("allUsers", allUsersPage.getContent());
        model.addAttribute("allUsersPages", allUsersPage.getTotalPages());
        model.addAttribute("reportedUsers", reportedUsersPage.getContent());
        model.addAttribute("reportedUsersPages",
                reportedUsersPage.getTotalPages());
        model.addAttribute("bannedUsers", bannedUsersPage.getContent());
        model.addAttribute("bannedUsersPages", bannedUsersPage.getTotalPages());
        return "users";
    }

    @GetMapping("/admin/users/{page}")
    @ResponseBody
    @Transactional
    public String users(@PathVariable int page,
                        @RequestParam(required = false) String sort,
                        @RequestParam(required = false) String roles,
                        @RequestParam(required = false) String countries,
                        @RequestParam(required = false) String banStatuses,
                        @RequestParam(required = false) String name) {
        List<UserForUsersListDTO> allUsers = userService.findUserForUsersListAll();
        List<CommentForReportedListDTO> reportedUsers = commentService.findByReportedTrue();
        List<UserForBannedListDTO> bannedUsers = userService.findByBannedTrue();
        List<Role> availableRoles = (roles == null) ? null :
                Arrays.stream(roles.split(",")).map(Role::valueOf).collect(Collectors.toList());
        List<Country> availableCountries = (countries == null) ? null :
                Arrays.stream(countries.split(",")).map(Country::valueOf).collect(Collectors.toList());
        List<Boolean> availableBannedStatuses = (banStatuses == null) ? null :
                Arrays.stream(banStatuses.split(",")).map(Boolean::valueOf).collect(Collectors.toList());
        List<String> nameResult = (name == null) ? null :
                userService.findUserForUsersListByFirstNameOrLastNameOrUsername(name, name, name).stream()
                        .map(UserForUsersListDTO::getId).collect(Collectors.toList());
        if (roles != null && countries != null && banStatuses != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()) &&
                        availableCountries.contains(user.getCountry()) &&
                        availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getUser().getRole()) &&
                        availableCountries.contains(user.getUser().getCountry()) &&
                        availableBannedStatuses.contains(user.getUser().isBanned()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()) &&
                        availableCountries.contains(user.getCountry()) &&
                        availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
        } else if (roles != null && countries != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()) &&
                        availableCountries.contains(user.getCountry()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getUser().getRole()) &&
                        availableCountries.contains(user.getUser().getCountry()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()) &&
                        availableCountries.contains(user.getCountry()));
            }).collect(Collectors.toList());
        } else if (roles != null && banStatuses != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()) &&
                        availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getUser().getRole()) &&
                        availableBannedStatuses.contains(user.getUser().isBanned()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()) &&
                        availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
        } else if (countries != null && banStatuses != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableCountries.contains(user.getCountry()) &&
                        availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableCountries.contains(user.getUser().getCountry()) &&
                        availableBannedStatuses.contains(user.getUser().isBanned()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableCountries.contains(user.getCountry()) &&
                        availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
        } else if (roles != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getUser().getRole()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableRoles.contains(user.getRole()));
            }).collect(Collectors.toList());
        } else if (countries != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableCountries.contains(user.getCountry()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableCountries.contains(user.getUser().getCountry()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableCountries.contains(user.getCountry()));
            }).collect(Collectors.toList());
        } else if (banStatuses != null) {
            allUsers = allUsers.stream().filter(user -> {
                return (availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
            reportedUsers = reportedUsers.stream().filter(user -> {
                return (availableBannedStatuses.contains(user.getUser().isBanned()));
            }).collect(Collectors.toList());
            bannedUsers = bannedUsers.stream().filter(user -> {
                return (availableBannedStatuses.contains(user.isBanned()));
            }).collect(Collectors.toList());
        }
        if (name != null) {
            allUsers =
                    allUsers.stream().filter(user -> nameResult.contains(user.getId())).collect(Collectors.toList());
            reportedUsers =
                    reportedUsers.stream().filter(user -> nameResult.contains(user.getUser().getId()))
                            .collect(Collectors.toList());
            bannedUsers =
                    bannedUsers.stream().filter(user -> nameResult.contains(user.getId())).collect(Collectors.toList());
        }
        if (sort != null) {
            if (sort.equals("username")) {
                allUsers =
                        allUsers.stream().sorted(new UsersByUsernameComparator()).collect(Collectors.toList());
                reportedUsers =
                        reportedUsers.stream().sorted(new UsersByUsernameComparator()).collect(Collectors.toList());
                bannedUsers =
                        bannedUsers.stream().sorted(new UsersByUsernameComparator()).collect(Collectors.toList());
            } else if (sort.equals("realname")) {
                allUsers =
                        allUsers.stream().sorted(new UsersByRealNameComparator()).collect(Collectors.toList());
            }
        }
        Page<UserForUsersListDTO> allUsersPage = new PageImpl<>(allUsers,
                PageRequest.of(page - 1, 14), allUsers.size());
        Page<CommentForReportedListDTO> reportedUsersPage =
                new PageImpl<>(reportedUsers, PageRequest.of(page - 1, 21),
                        reportedUsers.size());
        Page<UserForBannedListDTO> bannedUsersPage = new PageImpl<>(bannedUsers,
                PageRequest.of(page - 1, 14), bannedUsers.size());
        return new JSONArray(Arrays.asList(allUsersPage.getTotalPages(),
                allUsersPage.getContent(), reportedUsersPage.getTotalPages(),
                reportedUsersPage.getContent(), bannedUsersPage.getTotalPages(),
                bannedUsersPage.getContent())).toString();
    }

    @PostMapping("/admin/user/{id}/{role}")
    @ResponseBody
    @Transactional
    public String changeRole(@PathVariable String id,
                             @PathVariable String role) {
        try {
            UserForUsersListDTO user = userService.findUserForUsersListById(id);
            user.setRole(Role.valueOf(role));
            userService.updateRole(user);
            return "success";
        } catch (Exception ignore) {
        }
        return "failed";
    }

    @PostMapping("/admin/user/{id}/ban")
    @ResponseBody
    @Transactional
    public String ban(@PathVariable String id,
                      @RequestParam(required = false) Integer dateTo) {
        try {
            UserForUsersListDTO user = userService.findUserForUsersListById(id);
            user.setBanned(true);
            LocalDate bannedTo = null;
            if (dateTo != null) {
                bannedTo = LocalDate.now().plusDays(dateTo);
            }
            userService.updateBannedStatus(user, bannedTo);
            return "success";
        } catch (Exception ignore) {
        }
        return "failed";
    }

    @PostMapping("/admin/user/{id}/unban")
    @ResponseBody
    @Transactional
    public String unban(@PathVariable String id) {
        try {
            UserForUsersListDTO user = userService.findUserForUsersListById(id);
            user.setBanned(false);
            userService.updateBannedStatus(user, null);
            return "success";
        } catch (Exception ignore) {
        }
        return "failed";
    }

    @PostMapping("/admin/comment/{id}/skip")
    @ResponseBody
    @Transactional
    public String skipReportedComment(@PathVariable String id) {
        try {
            CommentForReportedListDTO comment =
                    commentService.findCommentForReportedListById(id);
            commentService.updateReportedStatus(comment, false);
            return "success";
        } catch (Exception ignore) {
        }
        return "failed";
    }
}
