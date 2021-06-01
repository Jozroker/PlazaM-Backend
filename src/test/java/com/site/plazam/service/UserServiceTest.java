package com.site.plazam.service;

import com.site.plazam.domain.Country;
import com.site.plazam.domain.Role;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    static MessageForUserDTO message;
    static TicketSimpleDTO ticket;
    static String movie1Id = "60b24466e5ff992bedb79ef0";
    static String movie2Id = "60b24466e5ff992bedb79ef1";
    static UserForRegistrationDTO userForRegistrationDTO;
    static UserForSelfInfoDTO user;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;
    @Autowired
    MovieService movieService;
    @Autowired
    MessageService messageService;
    List<UserForUsersListDTO> userForUsersListDTOList;
    List<UserForBannedListDTO> userForBannedListDTOList;

    @Test
    @Order(1)
    void failureSave1() {
        userForRegistrationDTO = new UserForRegistrationDTO();
        userForRegistrationDTO.setUsername("User");
        userForRegistrationDTO.setEmail("stiv@gmail.com");
        userForRegistrationDTO.setRegisterPassword("stiv2021");
        userForRegistrationDTO.setConfirmPassword("stiv2021");
        user = userService.save(userForRegistrationDTO);
        Assert.assertNull(user);
    }

    @Test
    @Order(2)
    void failureSave2() {
        userForRegistrationDTO = new UserForRegistrationDTO();
        userForRegistrationDTO.setUsername("stivy");
        userForRegistrationDTO.setEmail("user@gmail.com");
        userForRegistrationDTO.setRegisterPassword("stiv2021");
        userForRegistrationDTO.setConfirmPassword("stiv2021");
        user = userService.save(userForRegistrationDTO);
        Assert.assertNull(user);
    }

    @Test
    @Order(3)
    void failureSave3() {
        userForRegistrationDTO = new UserForRegistrationDTO();
        userForRegistrationDTO.setUsername("stivy");
        userForRegistrationDTO.setEmail("stiv@gmail.com");
        userForRegistrationDTO.setRegisterPassword("stiv2021");
        userForRegistrationDTO.setConfirmPassword("stiv2020");
        user = userService.save(userForRegistrationDTO);
        Assert.assertNull(user);
    }

    @Test
    @Order(4)
    void save() {
        userForRegistrationDTO = new UserForRegistrationDTO();
        userForRegistrationDTO.setFirstName("Stive");
        userForRegistrationDTO.setLastName("Jobs");
        userForRegistrationDTO.setUsername("stivy");
        userForRegistrationDTO.setEmail("stiv@gmail.com");
        userForRegistrationDTO.setRegisterPassword("stiv2021");
        userForRegistrationDTO.setConfirmPassword("stiv2021");
        user = userService.save(userForRegistrationDTO);
        System.out.println(user.toString());
        Assert.assertNotNull(user);
    }

    @Test
    @Order(5)
    void updateInfo() {
        user.setAboutMe("some info");
        user.setCountry(Country.UKRAINE);
        user = userService.updateInfo(user);
        System.out.println(user.toString());
        Assert.assertNotNull(user.getCountry());
    }

    @Test
    @Order(6)
    void updateLists() {
        message = messageService.findById("60b24466e5ff992bedb79eea");
        ticket = ticketService.findById("60b24466e5ff992bedb79eff");
        user.setViewedMovieIds(com.sun.tools.javac.util.List.of(movie1Id));
        user.setWaitMovieIds(com.sun.tools.javac.util.List.of(movie2Id));
        user.setMessages(com.sun.tools.javac.util.List.of(message));
        user.setTickets(com.sun.tools.javac.util.List.of(ticket));
        user = userService.updateLists(user);
        System.out.println(user.toString());
        Assert.assertFalse(user.getWaitMovieIds().isEmpty());
    }

    @Test
    @Order(7)
    void removeMovieFromAllUsersList() {
        MovieForMoviesListDTO movie = movieService.findMovieForMoviesListById("60b24466e5ff992bedb79ef0");
        userService.removeMovieFromFavouriteMoviesList(movie);
        userService.removeMovieFromViewedMoviesList(movie);
        userService.removeMovieFromWaitMoviesList(movie);
    }

    @Test
    @Order(8)
    void updateEmailFailure() {
        UserForSelfInfoDTO user2 = userService.updateEmail(user, "admin@gmail.com");
        if (user2 != null) {
            user = user2;
        }
        System.out.println(user.toString());
        Assert.assertNull(user2);
    }

    @Test
    @Order(9)
    void updatePhoneFailure() {
        UserForSelfInfoDTO user2 = userService.updatePhone(user, "+380506693794");
        if (user2 != null) {
            user = user2;
        }
        System.out.println(user.toString());
        Assert.assertNull(user2);
    }

    @Test
    @Order(10)
    void updateEmailAndPhone() {
        user = userService.updateEmail(user, "newemail@gmail.com");
        user = userService.updatePhone(user, "newphone");
        System.out.println(user.toString());
    }

    @Test
    @Disabled
    void failUpdatePassword() {
        user = userService.findUserForSelfInfoById("60b25a50f359f9510bcb884c");
        user = userService.updatePassword(user, "old", "newPass", "newPass");
        Assert.assertNull(user);
    }

    @Test
    @Disabled
    void updatePassword() {
        user = userService.findUserForSelfInfoById("60b25a50f359f9510bcb884c");
        user = userService.updatePassword(user, "stiv2021", "newPass",
                "newPass");
        Assert.assertNotNull(user);
    }

    @Test
    @Order(11)
    void updateBannedStatus() {
        user.setBanned(true);
        userService.updateBannedStatus(user, LocalDate.of(2021, 5, 30));
        user = userService.findUserForSelfInfoById(user.getId());
        System.out.println(user.toString());
        Assert.assertTrue(user.isBanned());
    }

    @Test
    @Order(12)
    void updateRole() {
        user.setRole(Role.WORKER);
        userService.updateRole(user);
        user = userService.findUserForSelfInfoById(user.getId());
        System.out.println(user.toString());
        Assert.assertEquals(user.getRole(), Role.WORKER);
    }

    @Test
    @Order(13)
    void findById() {
        user = userService.findUserForSelfInfoById(user.getId());
        Assert.assertNotNull(user);
    }

    @Test
    @Order(14)
    void findAll() {
        userForUsersListDTOList = userService.findUserForUsersListAll();
        System.out.println("[");
        userForUsersListDTOList.forEach(user -> System.out.println(user.toString()));
        System.out.println("]");
        Assert.assertFalse(userForUsersListDTOList.isEmpty());
    }

    @Test
    @Order(15)
    void findByRole() {
        userForUsersListDTOList =
                userService.findUserForUsersListByRole(Role.WORKER);
        System.out.println("[");
        userForUsersListDTOList.forEach(user -> System.out.println(user.toString()));
        System.out.println("]");
        Assert.assertFalse(userForUsersListDTOList.isEmpty());
    }

    @Test
    @Order(16)
    void findByHomeCountry() {
        userForUsersListDTOList =
                userService.findUserForUsersListByHomeCountry(Country.UKRAINE);
        System.out.println("[");
        userForUsersListDTOList.forEach(user -> System.out.println(user.toString()));
        System.out.println("]");
        Assert.assertFalse(userForUsersListDTOList.isEmpty());
    }

    @Test
    @Order(17)
    void findByUsernameOrEmail() {
        user = userService.findByUsernameOrEmail("stivy", "stiv@gmail.com");
        System.out.println(user.toString());
        Assert.assertNotNull(user);
    }

    @Test
    @Order(18)
    void findByBannedTrue() {
        userForBannedListDTOList = userService.findByBannedTrue();
        System.out.println("[");
        userForBannedListDTOList.forEach(user -> System.out.println(user.toString()));
        System.out.println("]");
        Assert.assertFalse(userForBannedListDTOList.isEmpty());
    }

    @Test
    @Order(19)
    void findByBannedToDate() {
        userForUsersListDTOList =
                userService.findByBannedToDate(LocalDate.of(2021, 5, 30));
        System.out.println("[");
        userForUsersListDTOList.forEach(user -> System.out.println(user.toString()));
        System.out.println("]");
        Assert.assertFalse(userForUsersListDTOList.isEmpty());
    }

    @Test
    @Order(20)
    void findByFNameOrLNameOrUsername() {
        userForUsersListDTOList =
                userService.findUserForUsersListByFirstNameOrLastNameOrUsername("admin", "user", "actor");
        System.out.println("[");
        userForUsersListDTOList.forEach(user -> System.out.println(user.toString()));
        System.out.println("]");
        Assert.assertFalse(userForUsersListDTOList.isEmpty());
    }

    @Test
    @Order(21)
    void delete() {
        user = userService.findUserForSelfInfoById("60b25a50f359f9510bcb884c");
        userService.delete(user);
        user = userService.findUserForSelfInfoById("60b25a50f359f9510bcb884c");
        Assert.assertNull(user);
    }
}
