package com.site.plazam.service.impl;

import com.site.plazam.domain.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           @Lazy PictureService pictureService,
                           MongoTemplate mongoTemplate,
                           @Lazy TicketService ticketService,
                           @Lazy CommentService commentService,
                           @Lazy RatingService ratingService,
                           @Lazy MessageService messageService) {
        this.ur = userRepository;
        this.um = userMapper;
        this.mt = mongoTemplate;
        this.ps = pictureService;
        this.ts = ticketService;
        this.cs = commentService;
        this.rs = ratingService;
        this.ms = messageService;
    }

    @Override
    public UserForSelfInfoDTO save(UserForRegistrationDTO userForRegistrationDTO) {
        return um.toUserForSelfInfoDTO(ur.save(um.toEntity(userForRegistrationDTO)));
    }

    @Override
    public UserForUsersListDTO updateBannedStatus(UserForUsersListDTO userForUsersListDTO, LocalDate bannedTo) {
        Query query =
                new Query(Criteria.where("id").is(userForUsersListDTO.getId()));
        Update update = new Update().set("banned",
                userForUsersListDTO.isBanned()).set("bannedToDate", bannedTo);
        return um.toUserForUsersListDTO(mt.findAndModify(query, update,
                User.class));
    }

    @Override
    @Transactional
    public UserForSelfInfoDTO updateInfo(String id,
                                         PictureDTO picture,
                                         String username,
                                         String firstName,
                                         String lastName,
                                         Sex sex,
                                         Country country,
                                         String city,
                                         String about,
                                         boolean hide18PlusMovies,
                                         boolean useLightTheme) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update()
                .set("username", username).set("firstName", firstName)
                .set("lastName", lastName).set("sex", sex)
                .set("homeCountry", country).set("homeCity", city)
                .set("aboutMe", about).set("useLightTheme", useLightTheme)
                .set("hide18PlusMovies", hide18PlusMovies);
        if (picture != null) {
            if (picture.getId() == null) {
                UserForCommentDTO user = findUserForCommentById(id);
                ps.delete(user.getPicture(), UserPicture.class);
                picture = ps.save(picture, UserPicture.class);
                update.set("pictureId", picture.getId());
            }
        }
        return um.toUserForSelfInfoDTO(mt.findAndModify(query, update, User.class));
    }

    @Override
    public UserForSelfInfoDTO updatePassword(String id,
                                             String oldPassword,
                                             String newPassword,
                                             String confirmPassword) {
        //todo create this use bcryptencoder
        return null;
    }

    @Override
    public UserForSelfInfoDTO updateEmail(String id, String email) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("email", email);
        return um.toUserForSelfInfoDTO(mt.findAndModify(query, update,
                User.class));
    }

    @Override
    public UserForSelfInfoDTO updatePhone(String id, String phone) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("phone", phone);
        return um.toUserForSelfInfoDTO(mt.findAndModify(query, update,
                User.class));
    }

    @Override
    public void unbanBanned(UserForBannedListDTO userForBannedListDTO) {
        Query query =
                new Query(Criteria.where("id").is(userForBannedListDTO.getId()));
        Update update = new Update().set("banned", false).set("bannedToDate",
                null);
        mt.findAndModify(query, update, User.class);
    }

    @Override
    public void banReported(UserForReportedListDTO userForReportedListDTO, LocalDate bannedTo) {
        Query query =
                new Query(Criteria.where("id").is(userForReportedListDTO.getId()));
        Update update = new Update().set("banned", true).set("bannedToDate",
                bannedTo);
        mt.findAndModify(query, update, User.class);
    }

    @Override
    @Transactional
    public UserForUsersListDTO updateRole(UserForUsersListDTO userForUsersListDTO) {
        Query query =
                new Query(Criteria.where("id").is(userForUsersListDTO.getId()));
        Update update = new Update().set("role",
                userForUsersListDTO.getRole());
        return um.toUserForUsersListDTO(mt.findAndModify(query, update,
                User.class));
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
        return ur.findAll().stream().map(um::toUserForBannedListDTO).collect(Collectors.toList());
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
    public UserForSelfInfoDTO findByUsernameOrEmail(UserForLoginDTO userForLoginDTO) {
        return ur.findByUsernameOrEmail(userForLoginDTO.getEmailOrUsername(),
                userForLoginDTO.getEmailOrUsername()).map(um::toUserForSelfInfoDTO).orElse(null);
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
    public UserForSelfInfoDTO updateTicketList(UserForSelfInfoDTO user, String ticketId) {
        List<String> newTicketList =
                user.getTickets().stream().map(TicketSimpleDTO::getId).filter(id -> !id.equals(ticketId)).collect(Collectors.toList());
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("ticketIds", newTicketList);
        return um.toUserForSelfInfoDTO(mt.findAndModify(query, update,
                User.class));
    }

    @Override
    public UserForSelfInfoDTO updateMessageList(UserForSelfInfoDTO user, String messageId) {
        List<String> newMessageList =
                user.getMessages().stream().map(MessageForUserDTO::getId).filter(id -> !id.equals(messageId)).collect(Collectors.toList());
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("messageIds", newMessageList);
        return um.toUserForSelfInfoDTO(mt.findAndModify(query, update,
                User.class));
    }

    @Override
    public void deleteMovieFromFavouriteMoviesList(MovieSimpleDTO movie) {
        List<UserForSelfInfoDTO> users = ur.findByFavouriteMovieIdsContains(movie.getId())
                .stream().map(um::toUserForSelfInfoDTO).collect(Collectors.toList());
        for (UserForSelfInfoDTO user : users) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            List<String> newFavouriteList =
                    user.getFavouriteMovies().stream()
                            .map(MovieForMoviesListDTO::getId)
                            .filter(id -> !id.equals(movie.getId()))
                            .collect(Collectors.toList());
            Update update = new Update().set("favouriteMovieIds",
                    newFavouriteList);
            mt.findAndModify(query, update, User.class);
        }

    }

    @Override
    public void deleteMovieFromViewedMoviesList(MovieSimpleDTO movie) {
        List<UserForSelfInfoDTO> users = ur.findByViewedMovieIdsContains(movie.getId())
                .stream().map(um::toUserForSelfInfoDTO).collect(Collectors.toList());
        for (UserForSelfInfoDTO user : users) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            List<String> newViewedList =
                    user.getViewedMovies().stream()
                            .map(MovieForMoviesListDTO::getId)
                            .filter(id -> !id.equals(movie.getId()))
                            .collect(Collectors.toList());
            Update update = new Update().set("viewedMovieIds",
                    newViewedList);
            mt.findAndModify(query, update, User.class);
        }

    }

    @Override
    public void deleteMovieFromWaitMoviesList(MovieSimpleDTO movie) {
        List<UserForSelfInfoDTO> users = ur.findByWaitMovieIdsContains(movie.getId())
                .stream().map(um::toUserForSelfInfoDTO).collect(Collectors.toList());
        for (UserForSelfInfoDTO user : users) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            List<String> newWaitList =
                    user.getWaitMovies().stream()
                            .map(MovieForMoviesListDTO::getId)
                            .filter(id -> !id.equals(movie.getId()))
                            .collect(Collectors.toList());
            Update update = new Update().set("waitMovieIds",
                    newWaitList);
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
        rs.findByUser(userSearch).forEach(rs::delete);
        cs.findByUser(userSearch).forEach(cs::delete);
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
