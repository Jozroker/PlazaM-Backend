package com.site.plazam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.plazam.domain.Country;
import com.site.plazam.domain.Lang;
import com.site.plazam.domain.Role;
import com.site.plazam.domain.Sex;
import com.site.plazam.dto.CommentForCommentsListDTO;
import com.site.plazam.dto.UserForLoginDTO;
import com.site.plazam.dto.UserForRegistrationDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.comparator.CommentsByDateComparator;
import com.site.plazam.dto.comparator.CommentsByMovieNameComparator;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
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

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final CinemaService cinemaService;

    private final UserService userService;

    private final SeanceService seanceService;

    private final CommentService commentService;

    private final MessageService messageService;

    private final TicketService ticketService;

    private final MovieService movieService;

    public UserController(CinemaService cinemaService,
                          UserService userService,
                          SeanceService seanceService,
                          CommentService commentService,
                          MessageService messageService,
                          MovieService movieService,
                          TicketService ticketService) {
        this.cinemaService = cinemaService;
        this.userService = userService;
        this.seanceService = seanceService;
        this.commentService = commentService;
        this.messageService = messageService;
        this.ticketService = ticketService;
        this.movieService = movieService;
    }

    @GetMapping("/authorize")
    public String authorize(ModelMap model, Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        UserForLoginDTO userForLoginDTO = new UserForLoginDTO();
        UserForRegistrationDTO userForRegistrationDTO =
                new UserForRegistrationDTO();
        model.addAttribute("loginUser", userForLoginDTO);
        model.addAttribute("registerUser", userForRegistrationDTO);
        return "authorize";
    }

    @PostMapping("/ticket/{id}/remove")
    @ResponseBody
    @Transactional
    public String removeTicket(@PathVariable String id) {
        try {
            ticketService.delete(ticketService.findById(id));
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @PostMapping(value = "/ticket/buy", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
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
        return "/movie/" + object.getString("movieId") + "?cinemaId=" +
                object.getString("cinemaId");
    }

    @PostMapping("/message/{id}/remove")
    @Transactional
    public String removeMessage(@PathVariable String id) {
        try {
            messageService.delete(messageService.findById(id));
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @PostMapping("/register")
//    @ResponseBody
    public String register(@ModelAttribute("registerUser") UserForRegistrationDTO user, ModelMap model) {
        userService.save(user);
        UserForLoginDTO userForLoginDTO = new UserForLoginDTO();
        UserForRegistrationDTO userForRegistrationDTO =
                new UserForRegistrationDTO();
        model.addAttribute("loginUser", userForLoginDTO);
        model.addAttribute("registerUser", userForRegistrationDTO);
        return "redirect:/authorize";
    }

    @PostMapping(value = "/user/{id}/update/info", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional
    public String updateInfo(@PathVariable String id,
                             @RequestBody String object) throws JsonProcessingException {
        UserForSelfInfoDTO user = userService.findUserForSelfInfoById(id);
        Object obj = new ObjectMapper().readValue(object, Object.class);
        if (((LinkedHashMap) obj).get("image") != null) {
            if (user.getPicture() != null) {
                if (!user.getPicture().getPictureString().equals(((LinkedHashMap) obj).get("image"))) {
                    PictureDTO picture = new PictureDTO();
                    picture.setFormat(((LinkedHashMap) obj).get("format").toString());
                    picture.setPicture(Base64.getDecoder().decode(((LinkedHashMap) obj).get("image").toString()));
                    user.setPicture(picture);
                }
            } else {
                PictureDTO picture = new PictureDTO();
                picture.setFormat(((LinkedHashMap) obj).get("format").toString());
                picture.setPicture(Base64.getDecoder().decode(((LinkedHashMap) obj).get("image").toString()));
                user.setPicture(picture);
            }
        }
        if (((LinkedHashMap) obj).get("hide18movies") != null) {
            user.setHide18PlusMovies(Boolean.parseBoolean(((LinkedHashMap) obj).get(
                    "hide18movies").toString()));
        }
        if (((LinkedHashMap) obj).get("language") != null) {
            user.setSelectedLang(Lang.valueOf(((LinkedHashMap) obj).get(
                    "language").toString()));
        }
        if (((LinkedHashMap) obj).get("cinemaId") != null) {
            user.setSelectedCinema(cinemaService.findById(((LinkedHashMap) obj).get(
                    "cinemaId").toString()));
        }
        if (((LinkedHashMap) obj).get("useLightTheme") != null) {
            user.setUseLightTheme(Boolean.parseBoolean(((LinkedHashMap) obj).get(
                    "useLightTheme").toString()));
        }
        if (((LinkedHashMap) obj).get("username") != null) {
            user.setUsername(((LinkedHashMap) obj).get("username").toString());
        }
        if (((LinkedHashMap) obj).get("firstName") != null) {
            user.setFirstName(((LinkedHashMap) obj).get("firstName").toString());
        }
        if (((LinkedHashMap) obj).get("lastName") != null) {
            user.setLastName(((LinkedHashMap) obj).get("lastName").toString());
        }
        if (((LinkedHashMap) obj).get("sex") != null) {
            user.setSex(Sex.valueOf(((LinkedHashMap) obj).get("sex").toString()));
        }
        if (((LinkedHashMap) obj).get("country") != null) {
            user.setCountry(Country.valueOf(((LinkedHashMap) obj).get("country").toString()));
        }
        if (((LinkedHashMap) obj).get("city") != null) {
            user.setHomeCity(((LinkedHashMap) obj).get("city").toString());
        }
        if (((LinkedHashMap) obj).get("about") != null) {
            user.setAboutMe(((LinkedHashMap) obj).get("about").toString());
        }
        userService.updateInfo(user);
        return "success";
    }

    @PostMapping(value = "/user/{id}/update/password", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional
    public String updatePassword(@PathVariable String id,
                                 @RequestBody String object) throws JsonProcessingException {
        UserForSelfInfoDTO user = userService.findUserForSelfInfoById(id);
        Object obj = new ObjectMapper().readValue(object, Object.class);
        if (((LinkedHashMap) obj).get("oldPassword") != null && ((LinkedHashMap) obj).get("newPassword") != null &&
                ((LinkedHashMap) obj).get("confirmPassword") != null) {
            user = userService.updatePassword(user,
                    ((LinkedHashMap) obj).get("oldPassword").toString(),
                    ((LinkedHashMap) obj).get("newPassword").toString(),
                    ((LinkedHashMap) obj).get("confirmPassword").toString());
            if (user != null) {
                return "success";
            }
        }
        return "failed";
    }

    @PostMapping(value = "/user/{id}/update/email", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String updateEmail(@PathVariable String id,
                              @RequestBody String object) throws JsonProcessingException {
        UserForSelfInfoDTO user = userService.findUserForSelfInfoById(id);
        Object obj = new ObjectMapper().readValue(object, Object.class);
        if (((LinkedHashMap) obj).get("email") != null) {
            user = userService.updateEmail(user,
                    ((LinkedHashMap) obj).get("email").toString());
            if (user != null) {
                return "success";
            }
        }
        return "failed";
    }

    @PostMapping(value = "/user/{id}/update/phone", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String updatePhone(@PathVariable String id,
                              @RequestBody String object) throws JsonProcessingException {
        UserForSelfInfoDTO user = userService.findUserForSelfInfoById(id);
        Object obj = new ObjectMapper().readValue(object, Object.class);
        if (((LinkedHashMap) obj).get("phone") != null) {
            user = userService.updatePhone(user,
                    ((LinkedHashMap) obj).get("phone").toString());
            if (user != null) {
                return "success";
            }
        }
        return "failed";
    }

    @PostMapping(value = "/user/{id}/update/lists", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional
    public String updateLists(@PathVariable String id,
                              @RequestBody String object) throws JsonProcessingException {
        UserForSelfInfoDTO user = userService.findUserForSelfInfoById(id);
        Object obj = new ObjectMapper().readValue(object, Object.class);
        if (user != null) {
            if (((LinkedHashMap) obj).get("addedFavouriteMovies") != null) {
                if (!((LinkedHashMap) obj).get("addedFavouriteMovies").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "addedFavouriteMovies").toString().length();
                    user.getFavouriteMovieIds().addAll(Arrays.asList(((LinkedHashMap) obj).get(
                            "addedFavouriteMovies").toString().substring(1,
                            length - 1).split(",")));
                }
            }
            if (((LinkedHashMap) obj).get("removedFavouriteMovies") != null) {
                if (!((LinkedHashMap) obj).get("removedFavouriteMovies").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "removedFavouriteMovies").toString().length();
                    user.getFavouriteMovieIds().removeAll(Arrays.asList(((LinkedHashMap) obj).get(
                            "removedFavouriteMovies").toString().substring(1,
                            length - 1).split(",")));
                }
            }
            if (((LinkedHashMap) obj).get("addedWaitedMovies") != null) {
                if (!((LinkedHashMap) obj).get("addedWaitedMovies").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "addedWaitedMovies").toString().length();
                    user.getWaitMovieIds().addAll(Arrays.asList(((LinkedHashMap) obj).get(
                            "addedWaitedMovies").toString().substring(1,
                            length - 1).split(",")));
                }
            }
            if (((LinkedHashMap) obj).get("removedWaitedMovies") != null) {
                if (!((LinkedHashMap) obj).get("removedWaitedMovies").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "removedWaitedMovies").toString().length();
                    user.getWaitMovieIds().removeAll(Arrays.asList(((LinkedHashMap) obj).get(
                            "removedWaitedMovies").toString().substring(1,
                            length - 1).split(",")));
                }
            }
            if (((LinkedHashMap) obj).get("addedViewedMovies") != null) {
                if (!((LinkedHashMap) obj).get("addedViewedMovies").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "addedViewedMovies").toString().length();
                    user.getViewedMovieIds().addAll(Arrays.asList(((LinkedHashMap) obj).get(
                            "addedViewedMovies").toString().substring(1,
                            length - 1).split(",")));
                }
            }
            if (((LinkedHashMap) obj).get("addedMessages") != null) {
                if (!((LinkedHashMap) obj).get("addedMessages").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "addedMessages").toString().length();
                    user.getMessages().addAll(Arrays.stream(((LinkedHashMap) obj).get(
                            "addedMessages").toString().substring(1,
                            length - 1).split(",")).map(messageService::findById).collect(Collectors.toList()));
                }
            }
            if (((LinkedHashMap) obj).get("removedMessages") != null) {
                if (!((LinkedHashMap) obj).get("removedMessages").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "removedMessages").toString().length();
                    user.getMessages().removeAll(Arrays.stream(((LinkedHashMap) obj).get(
                            "removedMessages").toString().substring(1,
                            length - 1).split(",")).map(messageService::findById).collect(Collectors.toList()));
                }
            }
            if (((LinkedHashMap) obj).get("addedTickets") != null) {
                if (!((LinkedHashMap) obj).get("addedTickets").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "addedTickets").toString().length();
                    user.getTickets().addAll(Arrays.stream(((LinkedHashMap) obj).get(
                            "addedTickets").toString().substring(1,
                            length - 1).split(",")).map(ticketService::findById).collect(Collectors.toList()));
                }
            }
            if (((LinkedHashMap) obj).get("removedTickets") != null) {
                if (!((LinkedHashMap) obj).get("removedTickets").toString().equals("[]")) {
                    int length = ((LinkedHashMap) obj).get(
                            "removedTickets").toString().length();
                    user.getTickets().removeAll(Arrays.stream(((LinkedHashMap) obj).get(
                            "removedTickets").toString().substring(1,
                            length - 1).split(",")).map(ticketService::findById).filter(ticket ->
                            LocalDate.now().plusDays(1).isBefore(ticket.getDate())).collect(Collectors.toList()));
                }
            }
            userService.updateLists(user);
            return "success";
        }
        return "failed";
    }

    @GetMapping("/user/comments")
    @Transactional
    public String comments(ModelMap model, Principal principal,
                           @RequestParam(required = false) String page) {
        int currentPage = page == null ? 0 : Integer.parseInt(page) - 1;
        UserForSelfInfoDTO user =
                userService.findByUsernameOrEmail(principal.getName(),
                        principal.getName());
        List<CommentForCommentsListDTO> commentsList =
                commentService.findByUser(user);
        Page<CommentForCommentsListDTO> comments =
                new PageImpl<>(commentsList.stream().sorted(new CommentsByDateComparator()).collect(Collectors.toList()),
                        PageRequest.of(currentPage, 10), commentsList.size());
        if (currentPage > 0 && comments.getContent().isEmpty()) {
            return "redirect:/error";
        }
        model.addAttribute("pagesCount", comments.getTotalPages());
        model.addAttribute("comments", comments.getContent());
        return "comments";
    }

    @GetMapping("/user/comments/{page}")
    @ResponseBody
    @Transactional
    public String comments(Principal principal,
                           @PathVariable int page,
                           @RequestParam(required = false) String sort) {
        UserForSelfInfoDTO user =
                userService.findByUsernameOrEmail(principal.getName(),
                        principal.getName());
        List<CommentForCommentsListDTO> commentsList =
                commentService.findByUser(user);
        Page<CommentForCommentsListDTO> comments =
                new PageImpl<>(new ArrayList<>());
        if (sort == null) {
            comments = new PageImpl<>(commentsList.stream().sorted(new CommentsByDateComparator()).collect(Collectors.toList()),
                    PageRequest.of(page - 1, 10), commentsList.size());
        } else {
            switch (sort) {
                case "date":
                    comments = new PageImpl<>(commentsList.stream().sorted(new CommentsByDateComparator()).collect(Collectors.toList()),
                            PageRequest.of(page - 1, 10), commentsList.size());
                    break;
                case "movie":
                    comments =
                            new PageImpl<>(commentsList.stream().sorted(new CommentsByMovieNameComparator()).collect(Collectors.toList()),
                                    PageRequest.of(page - 1, 10), commentsList.size());
                    break;
                default:
                    break;
            }
        }
        return new JSONArray(Arrays.asList(comments.getTotalPages(),
                comments.getContent())).toString();
    }

    @PostMapping("/comment/{id}/complain")
    @ResponseBody
    @Transactional
    public String complain(@PathVariable String id) {
        try {
            commentService.updateReportedStatus(commentService.findCommentForReportedListById(id), true);
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @PostMapping("/comment/{id}/remove")
    @ResponseBody
    @Transactional
    public String remove(@PathVariable String id) {
        try {
            commentService.delete(commentService.findCommentForCommentsListById(id));
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @PostMapping("/comment/{id}/update")
    @ResponseBody
    @Transactional
    public String update(@PathVariable String id, @RequestParam String text) {
        try {
            CommentForCommentsListDTO comment =
                    commentService.findCommentForCommentsListById(id);
            comment.setText(text);
            commentService.updateCommentText(comment);
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

//    @PostMapping("/login")
//    private void doAutoLogin(@ModelAttribute("loginUser") UserForLoginDTO user,
//                             HttpServletRequest request) {

//        try {
//            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
//            UsernamePasswordAuthenticationToken token =
//                    new UsernamePasswordAuthenticationToken(user.getEmailOrUsername(), user.getLoginPassword());
//            token.setDetails(new WebAuthenticationDetails(request));
//            Authentication authentication = this.authenticationProvider.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception e) {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        return;

//    }


}
