package com.site.plazam.service;

import com.site.plazam.domain.Country;
import com.site.plazam.domain.Role;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.MessageSimpleDTO;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserForSelfInfoDTO save(UserForRegistrationDTO userForRegistrationDTO);

    UserForSelfInfoDTO updateInfo(UserForSelfInfoDTO user);

    UserForSelfInfoDTO updatePassword(UserForSelfInfoDTO user,
                                      String oldPassword,
                                      String newPassword,
                                      String confirmPassword);

    UserForSelfInfoDTO updateLists(UserForSelfInfoDTO user);

    UserForSelfInfoDTO updateEmail(UserForSelfInfoDTO user, String email);

    UserForSelfInfoDTO updatePhone(UserForSelfInfoDTO user, String phone);

    UserForUsersListDTO updateBannedStatus(UserSimpleDTO user,
                                           LocalDate bannedTo);

    void unbanBanned(UserSimpleDTO user);

    void banReported(UserSimpleDTO user, LocalDate bannedTo);

    UserForUsersListDTO updateRole(UserSimpleDTO user);


    UserForSelfInfoDTO findByPhone(String phone);

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

    List<UserForUsersListDTO> findUserForUsersListByHomeCountry(Country country);

    Page<UserForUsersListDTO> findUserForUsersListByHomeCountry(Country country, Pageable pageable);

    List<UserForReportedListDTO> findUserForReportedListByHomeCountry(Country country);

    Page<UserForReportedListDTO> findUserForReportedListByHomeCountry(Country country, Pageable pageable);

    List<UserForBannedListDTO> findUserForBannedListByHomeCountry(Country country);

    Page<UserForBannedListDTO> findUserForBannedListByHomeCountry(Country country,
                                                                  Pageable pageable);


    List<UserForUsersListDTO> findUserForUsersListByRole(Role role);

    Page<UserForUsersListDTO> findUserForUsersListByRole(Role role, Pageable pageable);

    List<UserForBannedListDTO> findUserForBannedListByRole(Role role);

    Page<UserForBannedListDTO> findUserForBannedListByRole(Role role,
                                                           Pageable pageable);

    UserForSelfInfoDTO findByUsernameOrEmail(String username, String email);

    List<UserForBannedListDTO> findByBannedTrue();

    Page<UserForBannedListDTO> findByBannedTrue(Pageable pageable);

    List<UserForUsersListDTO> findByBannedToDate(LocalDate date);

    List<UserForUsersListDTO> findUserForUsersListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);

    Page<UserForUsersListDTO> findUserForUsersListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable);

    List<UserForBannedListDTO> findUserForBannedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);

    Page<UserForBannedListDTO> findUserForBannedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable);


    UserForSelfInfoDTO removeTicketFromTicketList(UserForSelfInfoDTO user,
                                                  TicketSimpleDTO ticket);

    UserForSelfInfoDTO removeMessageFromMessageList(UserForSelfInfoDTO user,
                                                    MessageSimpleDTO message);

    void removeMovieFromFavouriteMoviesList(MovieSimpleDTO movie);

    void removeMovieFromViewedMoviesList(MovieSimpleDTO movie);

    void removeMovieFromWaitMoviesList(MovieSimpleDTO movie);

    void delete(UserSimpleDTO user);

    void deleteAll();
}
