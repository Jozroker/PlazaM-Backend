package com.site.plazam.service.impl;

import com.site.plazam.domain.Role;
import com.site.plazam.domain.User;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.UserSimpleDTO;
import com.site.plazam.repository.UserRepository;
import com.site.plazam.service.UserService;
import com.site.plazam.service.mapper.UserMapper;
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

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper, MongoTemplate mongoTemplate) {
        this.ur = userRepository;
        this.um = userMapper;
        this.mt = mongoTemplate;
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
    public void delete(UserSimpleDTO user) {
        ur.deleteById(user.getId());
    }

    @Override
    public void deleteAll() {
        ur.deleteAll();
    }
}
