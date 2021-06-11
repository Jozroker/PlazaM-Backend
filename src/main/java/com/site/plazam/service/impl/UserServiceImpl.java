package com.site.plazam.service.impl;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.site.plazam.domain.Country;
import com.site.plazam.domain.Role;
import com.site.plazam.domain.User;
import com.site.plazam.domain.UserPicture;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.*;
import com.site.plazam.repository.UserRepository;
import com.site.plazam.service.*;
import com.site.plazam.service.mapper.UserMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository ur;

    private final UserMapper um;

    private final MongoTemplate mt;

    @Lazy
    private final PictureService ps;

    @Lazy
    private final TicketService ts;

    @Lazy
    private final CommentService cs;

    @Lazy
    private final RatingService rs;

    @Lazy
    private final MessageService ms;

    @Lazy
    private final CinemaService cms;

    BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           @Lazy PictureService pictureService,
                           MongoTemplate mongoTemplate,
                           @Lazy TicketService ticketService,
                           @Lazy CommentService commentService,
                           @Lazy RatingService ratingService,
                           @Lazy MessageService messageService,
                           @Lazy CinemaService cinemaService,
                           BCryptPasswordEncoder passwordEncoder) {
        this.ur = userRepository;
        this.um = userMapper;
        this.mt = mongoTemplate;
        this.ps = pictureService;
        this.ts = ticketService;
        this.cs = commentService;
        this.rs = ratingService;
        this.ms = messageService;
        this.cms = cinemaService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO save(UserForRegistrationDTO userForRegistrationDTO) {
        //get cinema by ip geolocation and then set it or default to register
        // model
        UserForSelfInfoDTO user =
                findByUsernameOrEmail(userForRegistrationDTO.getUsername(),
                        userForRegistrationDTO.getEmail());
        if (userForRegistrationDTO.getRegisterPassword().equals(userForRegistrationDTO.getConfirmPassword()) && user == null) {
            userForRegistrationDTO.setRegisterPassword(passwordEncoder.encode(userForRegistrationDTO.getRegisterPassword()));
            try (WebServiceClient client =
                         new WebServiceClient.Builder(559220,
                                 "7hh3snpPVY1uaoE4").host("geolite.info").build()) {
                URL checkIpService = new URL("http://checkip.amazonaws.com");
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        checkIpService.openStream()));
                String myPublicIp = br.readLine();

                CountryResponse countryResponse =
                        client.country(InetAddress.getByName(myPublicIp));
                CityResponse cityResponse =
                        client.city(InetAddress.getByName(myPublicIp));
                CinemaDTO cinema =
                        cms.findFirstByCountryAndCity(Country.valueOf(countryResponse.getCountry().getName().replace(' ', '_').toUpperCase()),
                                cityResponse.getCity().getName());
                if (cinema == null) {
                    cinema = cms.findAll().get(0);
                }
                userForRegistrationDTO.setSelectedCinema(cinema);
            } catch (Exception e) {
                userForRegistrationDTO.setSelectedCinema(cms.findAll().get(0));
            }
            return um.toUserForSelfInfoDTO(ur.save(um.toEntity(userForRegistrationDTO)));
        }
        return null;
    }

    @Override
    @Transactional
    public UserForUsersListDTO updateBannedStatus(UserSimpleDTO user,
                                                  LocalDate bannedTo) {
        Query query =
                new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("banned",
                user.isBanned()).set("bannedToDate", bannedTo);
        mt.findAndModify(query, update,
                User.class);
        return findUserForUsersListById(user.getId());
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO updateInfo(UserForSelfInfoDTO user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update()
                .set("username", user.getUsername()).set("firstName", user.getFirstName())
                .set("lastName", user.getLastName()).set("sex", user.getSex())
                .set("homeCountry", user.getCountry()).set("homeCity",
                        user.getHomeCity()).set("aboutMe", user.getAboutMe())
                .set("useLightTheme", user.isUseLightTheme())
                .set("hide18PlusMovies", user.isHide18PlusMovies())
                .set("selectedLang", user.getSelectedLang())
                .set("selectedCinemaId", user.getSelectedCinema().getId());
        if (user.getPicture() != null) {
            if (user.getPicture().getId() == null) {
                UserForCommentDTO userForComment =
                        findUserForCommentById(user.getId());
                ps.delete(userForComment.getPicture(), UserPicture.class);
                PictureDTO picture = ps.save(user.getPicture(), UserPicture.class);
                update.set("pictureId", picture.getId());
            }
        }
        mt.findAndModify(query, update, User.class);
        //todo mapping error???  .findAndModify().find(query) - correct
        return findUserForSelfInfoById(user.getId());
    }

    //    public UserForSelfInfoDTO updatePassword(String id,
//                                             String oldPassword,
//                                             String newPassword,
//                                             String con
    @Override
    @Transactional
    public UserForSelfInfoDTO updatePassword(UserForSelfInfoDTO user,
                                             String oldPassword,
                                             String newPassword,
                                             String passwordConfirm) {
//        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//        new BCryptPasswordEncoder().matches("roots", user.getPassword());
        //todo check this func
        User userFromDB = ur.findById(user.getId()).orElse(null);
        if (newPassword.equals(passwordConfirm) && passwordEncoder.matches(oldPassword, userFromDB.getPassword())) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("password",
                    passwordEncoder.encode(newPassword));
            mt.findAndModify(query, update,
                    User.class);
            return findUserForSelfInfoById(user.getId());
        }
        return null;
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO updateEmail(UserForSelfInfoDTO user, String email) {
        UserForSelfInfoDTO userExists = findByUsernameOrEmail(email, email);
        if (userExists == null) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("email", email);
            mt.findAndModify(query, update,
                    User.class);
            return findUserForSelfInfoById(user.getId());
        }
        return null;
    }

    @Override
    public UserForSelfInfoDTO findByPhone(String phone) {
        return ur.findByPhone(phone).map(um::toUserForSelfInfoDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO updateLists(UserForSelfInfoDTO user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        List<String> ticketIds =
                user.getTickets().stream().map(TicketSimpleDTO::getId).collect(Collectors.toList());
        List<String> messageIds =
                user.getMessages().stream().map(MessageSimpleDTO::getId).collect(Collectors.toList());
        Update update = new Update().set("ticketIds", ticketIds).set(
                "messageIds", messageIds).set("favouriteMovieIds",
                user.getFavouriteMovieIds()).set("viewedMovieIds",
                user.getViewedMovieIds()).set(
                "waitMovieIds", user.getWaitMovieIds());
        mt.findAndModify(query, update,
                User.class);
        return findUserForSelfInfoById(user.getId());
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO updatePhone(UserForSelfInfoDTO user, String phone) {
        UserForSelfInfoDTO userExists = findByPhone(phone);
        if (userExists == null) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("phone", phone);
            mt.findAndModify(query, update,
                    User.class);
            return findUserForSelfInfoById(user.getId());
        }
        return null;
    }

    @Override
    public void unbanBanned(UserSimpleDTO user) {
        Query query =
                new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("banned", false).set("bannedToDate",
                null);
        mt.findAndModify(query, update, User.class);
    }

    @Override
    @Deprecated
    public void banReported(UserSimpleDTO user, LocalDate bannedTo) {
        Query query =
                new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("banned", true).set("bannedToDate",
                bannedTo);
        mt.findAndModify(query, update, User.class);
    }

    @Override
    @Transactional
    public UserForUsersListDTO updateRole(UserSimpleDTO user) {
        Query query =
                new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("role",
                user.getRole());
        mt.findAndModify(query, update,
                User.class);
        return findUserForUsersListById(user.getId());
    }

    @Override
    public UserForBannedListDTO findUserForBannedListById(String id) {
        return ur.findById(id).map(um::toUserForBannedListDTO).orElse(null);
    }

    @Override
    public UserForCommentDTO findUserForCommentById(String id) {
        return ur.findById(id).map(um::toUserForCommentDTO).orElse(null);
    }

    @Override
    public UserForReportedListDTO findUserForReportedListById(String id) {
        return ur.findById(id).map(um::toUserForReportedListDTO).orElse(null);
    }

    @Override
    public UserForSelfInfoDTO findUserForSelfInfoById(String id) {
        return ur.findById(id).map(um::toUserForSelfInfoDTO).orElse(null);
    }

    @Override
    public UserForUsersListDTO findUserForUsersListById(String id) {
        return ur.findById(id).map(um::toUserForUsersListDTO).orElse(null);
    }

    @Override
    public List<UserForBannedListDTO> findUserForBannedListAll() {
        return ur.findByBannedTrue().stream().map(um::toUserForBannedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForBannedListDTO> findUserForBannedListAll(Pageable pageable) {
        return ur.findAll(pageable).map(um::toUserForBannedListDTO);
    }

    @Override
    public List<UserForResultListDTO> findUserForResultListAll() {
        return ur.findAll().stream().map(um::toUserForResultListDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserForUsersListDTO> findUserForUsersListAll() {
        return ur.findAll().stream().map(um::toUserForUsersListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForUsersListDTO> findUserForUsersListAll(Pageable pageable) {
        return ur.findAll(pageable).map(um::toUserForUsersListDTO);
    }

    @Override
    public List<UserForUsersListDTO> findUserForUsersListByRole(Role role) {
        return ur.findByRole(role).stream().map(um::toUserForUsersListDTO).collect(Collectors.toList());
    }

    @Override
    public UserForSelfInfoDTO findByTicketsContains(TicketSimpleDTO ticket) {
        return um.toUserForSelfInfoDTO(ur.findByTicketIdsContains(ticket.getId()).orElse(null));
    }

    @Override
    public Page<UserForUsersListDTO> findUserForUsersListByRole(Role role, Pageable pageable) {
        return ur.findByRole(role, pageable).map(um::toUserForUsersListDTO);
    }

    @Override
    public List<UserForBannedListDTO> findUserForBannedListByRole(Role role) {
        return ur.findByRole(role).stream().map(um::toUserForBannedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForBannedListDTO> findUserForBannedListByRole(Role role, Pageable pageable) {
        return ur.findByRole(role, pageable).map(um::toUserForBannedListDTO);
    }

    @Override
    public List<UserForUsersListDTO> findUserForUsersListByHomeCountry(Country country) {
        return ur.findByHomeCountry(country).stream().map(um::toUserForUsersListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForUsersListDTO> findUserForUsersListByHomeCountry(Country country, Pageable pageable) {
        return ur.findByHomeCountry(country, pageable).map(um::toUserForUsersListDTO);
    }

    @Override
    public List<UserForReportedListDTO> findUserForReportedListByHomeCountry(Country country) {
        return ur.findByHomeCountry(country).stream().map(um::toUserForReportedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForReportedListDTO> findUserForReportedListByHomeCountry(Country country, Pageable pageable) {
        return ur.findByHomeCountry(country, pageable).map(um::toUserForReportedListDTO);
    }

    @Override
    public List<UserForBannedListDTO> findUserForBannedListByHomeCountry(Country country) {
        return ur.findByHomeCountry(country).stream().map(um::toUserForBannedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForBannedListDTO> findUserForBannedListByHomeCountry(Country country, Pageable pageable) {
        return ur.findByHomeCountry(country, pageable).map(um::toUserForBannedListDTO);
    }

    @Override
    public UserForSelfInfoDTO findByUsernameOrEmail(String username,
                                                    String email) {
        return ur.findFirstByUsernameOrEmail(username, email).map(um::toUserForSelfInfoDTO).orElse(null);
    }

    @Override
    public List<UserForBannedListDTO> findByBannedTrue() {
        return ur.findByBannedTrue().stream().map(um::toUserForBannedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForBannedListDTO> findByBannedTrue(Pageable pageable) {
        return ur.findByBannedTrue(pageable).map(um::toUserForBannedListDTO);
    }

    @Override
    public List<UserForUsersListDTO> findByBannedToDate(LocalDate date) {
        return ur.findByBannedToDate(date).stream().map(um::toUserForUsersListDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserForUsersListDTO> findUserForUsersListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username) {
        return ur.findByFirstNameOrLastNameOrUsername(firstName, lastName,
                username).stream().map(um::toUserForUsersListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForUsersListDTO> findUserForUsersListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable) {
        return ur.findByFirstNameOrLastNameOrUsername(firstName, lastName,
                username, pageable).map(um::toUserForUsersListDTO);
    }

    @Override
    public List<UserForBannedListDTO> findUserForBannedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username) {
        return ur.findByFirstNameOrLastNameOrUsername(firstName, lastName,
                username).stream().map(um::toUserForBannedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserForBannedListDTO> findUserForBannedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable) {
        return ur.findByFirstNameOrLastNameOrUsername(firstName, lastName,
                username, pageable).map(um::toUserForBannedListDTO);
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO removeTicketFromTicketList(UserForSelfInfoDTO user, TicketSimpleDTO ticket) {
        List<String> newTicketList =
                user.getTickets().stream().map(TicketSimpleDTO::getId).filter(id -> !id.equals(ticket.getId())).collect(Collectors.toList());
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("ticketIds", newTicketList);
        mt.findAndModify(query, update,
                User.class);
        return findUserForSelfInfoById(user.getId());
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO removeMessageFromMessageList(UserForSelfInfoDTO user, MessageSimpleDTO message) {
        List<String> newMessageList =
                user.getMessages().stream().map(MessageForUserDTO::getId).filter(id -> !id.equals(message.getId())).collect(Collectors.toList());
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("messageIds", newMessageList);
        mt.findAndModify(query, update,
                User.class);
        return findUserForSelfInfoById(user.getId());
    }

    @Override
    @Transactional
    public void removeMovieFromFavouriteMoviesList(MovieSimpleDTO movie) {
        List<UserForSelfInfoDTO> users = ur.findByFavouriteMovieIdsContains(movie.getId())
                .stream().map(um::toUserForSelfInfoDTO).collect(Collectors.toList());
        for (UserForSelfInfoDTO user : users) {
            user.getFavouriteMovieIds().remove(movie.getId());
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("favouriteMovieIds",
                    user.getFavouriteMovieIds());
            mt.findAndModify(query, update, User.class);
        }

    }

    @Override
    @Transactional
    public void removeMovieFromViewedMoviesList(MovieSimpleDTO movie) {
        List<UserForSelfInfoDTO> users = ur.findByViewedMovieIdsContains(movie.getId())
                .stream().map(um::toUserForSelfInfoDTO).collect(Collectors.toList());
        for (UserForSelfInfoDTO user : users) {
            user.getViewedMovieIds().remove(movie.getId());
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("viewedMovieIds",
                    user.getViewedMovieIds());
            mt.findAndModify(query, update, User.class);
        }

    }

    @Override
    @Transactional
    public void removeMovieFromWaitMoviesList(MovieSimpleDTO movie) {
        List<UserForSelfInfoDTO> users = ur.findByWaitMovieIdsContains(movie.getId())
                .stream().map(um::toUserForSelfInfoDTO).collect(Collectors.toList());
        for (UserForSelfInfoDTO user : users) {
            user.getWaitMovieIds().remove(movie.getId());
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("waitMovieIds",
                    user.getWaitMovieIds());
            mt.findAndModify(query, update, User.class);
        }
    }

    @Override
    @Transactional
    public void delete(UserSimpleDTO user) {
        UserForSelfInfoDTO userSearch = findUserForSelfInfoById(user.getId());
        userSearch.getTickets().forEach(ts::delete);
        if (userSearch.getPicture() != null) {
            ps.delete(userSearch.getPicture(), UserPicture.class);
        }
        rs.deleteByUser(user);
        cs.deleteByUser(user);
        userSearch.getMessages().stream().map(message -> {
            MessageSimpleDTO messageSimpleDTO = new MessageSimpleDTO();
            messageSimpleDTO.setId(message.getId());
            return messageSimpleDTO;
        }).forEach(ms::delete);
        ur.deleteById(user.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        ts.deleteAll();
        cs.deleteAll();
        rs.deleteAll();
        ms.deleteAll();
        ur.deleteAll();
    }
}
