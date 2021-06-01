package com.site.plazam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.plazam.domain.Country;
import com.site.plazam.domain.Lang;
import com.site.plazam.domain.Sex;
import com.site.plazam.dto.UserForLoginDTO;
import com.site.plazam.dto.UserForRegistrationDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.service.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.LinkedHashMap;

@Controller
public class UserController {

    private final CinemaService cinemaService;

    private final UserService userService;

    private final SeanceService seanceService;

    private final HallService hallService;

    private final MovieService movieService;

    public UserController(CinemaService cinemaService,
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

    @GetMapping("/authorize")
    public String authorize(ModelMap model) {
        UserForLoginDTO userForLoginDTO = new UserForLoginDTO();
        UserForRegistrationDTO userForRegistrationDTO =
                new UserForRegistrationDTO();
        model.addAttribute("loginUser", userForLoginDTO);
        model.addAttribute("registerUser", userForRegistrationDTO);
        return "authorize";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute("registerUser") UserForRegistrationDTO user, ModelMap model) {
        userService.save(user);
        UserForLoginDTO userForLoginDTO = new UserForLoginDTO();
        UserForRegistrationDTO userForRegistrationDTO =
                new UserForRegistrationDTO();
        model.addAttribute("loginUser", userForLoginDTO);
        model.addAttribute("registerUser", userForRegistrationDTO);
        return "regirect:/authorize";
    }

    @PostMapping(value = "/user/{id}/update/info", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
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
