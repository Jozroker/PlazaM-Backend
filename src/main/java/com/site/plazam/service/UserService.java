package com.site.plazam.service;

import com.site.plazam.domain.Country;
import com.site.plazam.domain.Role;
import com.site.plazam.domain.Sex;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserForSelfInfoDTO save(UserForRegistrationDTO userForRegistrationDTO);

    UserForSelfInfoDTO updateInfo(String id, PictureDTO picture, String username,
                                  String firstName, String lastName, Sex sex,
                                  Country country, String city, String about,
                                  boolean hide18PlusMovies,
                                  boolean useLightTheme);

    UserForSelfInfoDTO updatePassword(String id, String oldPassword,
                                      String newPassword,
                                      String confirmPassword);

    UserForSelfInfoDTO updateEmail(String id, String email);

    UserForSelfInfoDTO updatePhone(String id, String phone);

    UserForUsersListDTO updateBannedStatus(UserForUsersListDTO userForUsersListDTO, LocalDate bannedTo);

    void unbanBanned(UserForBannedListDTO userForBannedListDTO);

    void banReported(UserForReportedListDTO userForReportedListDTO, LocalDate bannedTo);

    UserForUsersListDTO updateRole(UserForUsersListDTO userForUsersListDTO);


    UserForBannedListDTO findUserForBannedListById(String id);

    UserForCommentDTO findUserForCommentById(String id);

    UserForReportedListDTO findUserForReportedListById(String id);

    UserForSelfInfoDTO findUserForSelfInfoById(String id);

    UserForUsersListDTO findUserForUsersListById(String id);

    List<UserForBannedListDTO> findUserForBannedListAll();

    Page<UserForBannedListDTO> findUserForBannedListAll(Pageable pageable);

    List<UserForResultListDTO> findUserForResultListAll();

    List<UserForUsersListDTO> findUserForUsersListAll();

    Page<UserForUsersListDTO> findUserForUsersListAll(Pageable pageable);


    List<UserForUsersListDTO> findUserForUsersListByRole(Role role);

    Page<UserForUsersListDTO> findUserForUsersListByRole(Role role, Pageable pageable);

    List<UserForBannedListDTO> findUserForBannedListByRole(Role role);

    Page<UserForBannedListDTO> findUserForBannedListByRole(Role role,
                                                           Pageable pageable);

    UserForSelfInfoDTO findByUsernameOrEmail(UserForLoginDTO userForLoginDTO);

    List<UserForBannedListDTO> findByBannedTrue();

    Page<UserForBannedListDTO> findByBannedTrue(Pageable pageable);

    List<UserForUsersListDTO> findByBannedToDate(LocalDate date);

    List<UserForUsersListDTO> findUserForUsersListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);

    Page<UserForUsersListDTO> findUserForUsersListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable);

    List<UserForBannedListDTO> findUserForBannedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);

    Page<UserForBannedListDTO> findUserForBannedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable);


    UserForSelfInfoDTO updateTicketList(UserForSelfInfoDTO user,
                                        String ticketId);

    UserForSelfInfoDTO updateMessageList(UserForSelfInfoDTO user,
                                         String messageId);

    void deleteMovieFromFavouriteMoviesList(MovieSimpleDTO movie);

    void deleteMovieFromViewedMoviesList(MovieSimpleDTO movie);

    void deleteMovieFromWaitMoviesList(MovieSimpleDTO movie);

    void delete(UserSimpleDTO user);

    void deleteAll();
}
