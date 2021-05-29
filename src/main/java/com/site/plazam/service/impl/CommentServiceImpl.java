package com.site.plazam.service.impl;

import com.site.plazam.domain.Comment;
import com.site.plazam.domain.Role;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CommentSimpleDTO;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import com.site.plazam.repository.CommentRepository;
import com.site.plazam.service.CommentService;
import com.site.plazam.service.UserService;
import com.site.plazam.service.mapper.CommentMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository cr;

    private final CommentMapper cm;

    private final MongoTemplate mt;

    @Lazy
    private final UserService us;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              @Lazy UserService userService,
                              MongoTemplate mongoTemplate) {
        this.cr = commentRepository;
        this.cm = commentMapper;
        this.mt = mongoTemplate;
        this.us = userService;
    }

    @Override
    @Transactional
    public CommentForMovieDTO save(CommentCreateDTO commentCreateDTO) {
        commentCreateDTO.setTime(commentCreateDTO.getTime().withYear(1).withMonth(1)
                .withDayOfMonth(1).withSecond(0).withNano(0));
        return cm.toCommentForMovieDTO(cr.save(cm.toEntity(commentCreateDTO)));
    }

    @Override
    @Transactional
    public CommentForCommentsListDTO updateCommentText(CommentForCommentsListDTO commentForCommentsListDTO) {
        Query query =
                new Query(Criteria.where("id").is(commentForCommentsListDTO.getId()));
        Update update = new Update().set("text", commentForCommentsListDTO.getText());
        mt.findAndModify(query, update,
                Comment.class);
        return findCommentForCommentsListById(commentForCommentsListDTO.getId());
    }

    @Override
    public CommentForCommentsListDTO findCommentForCommentsListById(String id) {
        return cr.findById(id).map(cm::toCommentForCommentsListDTO).orElse(null);
    }

    @Override
    public CommentForMovieDTO findCommentForMovieById(String id) {
        return cr.findById(id).map(cm::toCommentForMovieDTO).orElse(null);
    }

    @Override
    public CommentForReportedListDTO findCommentForReportedListById(String id) {
        return cr.findById(id).map(cm::toCommentForReportedListDTO).orElse(null);
    }

    @Override
    public List<CommentForCommentsListDTO> findCommentForCommentsListAll() {
        return cr.findAll().stream().map(cm::toCommentForCommentsListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<CommentForCommentsListDTO> findCommentForCommentsListAll(Pageable pageable) {
        return cr.findAll(pageable).map(cm::toCommentForCommentsListDTO);
    }

    @Override
    public List<CommentForMovieDTO> findCommentForMovieAll() {
        return cr.findAll().stream().map(cm::toCommentForMovieDTO).collect(Collectors.toList());
    }

    @Override
    public Page<CommentForMovieDTO> findCommentForMovieAll(Pageable pageable) {
        return cr.findAll(pageable).map(cm::toCommentForMovieDTO);
    }

    @Override
    public List<CommentForReportedListDTO> findCommentForReportedListAll() {
        return cr.findAll().stream().map(cm::toCommentForReportedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<CommentForReportedListDTO> findCommentForReportedListAll(Pageable pageable) {
        return cr.findAll(pageable).map(cm::toCommentForReportedListDTO);
    }

    @Override
    public List<CommentForReportedListDTO> findCommentForReportedListByUserRole(Role role) {
        return cr.findAll().stream().map(cm::toCommentForReportedListDTO)
                .filter(comment -> comment.getUser().getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentForReportedListDTO> findCommentForReportedListByUserRole(Role role, Pageable pageable) {
        List<CommentForReportedListDTO> result = cr.findAll().stream().map(cm::toCommentForReportedListDTO)
                .filter(comment -> comment.getUser().getRole().equals(role))
                .collect(Collectors.toList());
        return new PageImpl<>(result);
    }

    @Override
    @Transactional
    public List<CommentForReportedListDTO> findCommentForReportedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username) {
        List<String> usersIds =
                us.findUserForUsersListByFirstNameOrLastNameOrUsername(firstName, lastName, username)
                        .stream().map(UserSimpleDTO::getId).collect(Collectors.toList());
        return cr.findAll().stream().map(cm::toCommentForReportedListDTO)
                .filter(comment -> usersIds.contains(comment.getUser().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentForReportedListDTO> findCommentForReportedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable) {
        List<String> usersIds =
                us.findUserForUsersListByFirstNameOrLastNameOrUsername(firstName, lastName, username)
                        .stream().map(UserSimpleDTO::getId).collect(Collectors.toList());
        List<CommentForReportedListDTO> result = cr.findAll().stream().map(cm::toCommentForReportedListDTO)
                .filter(comment -> usersIds.contains(comment.getUser().getId()))
                .collect(Collectors.toList());
        return new PageImpl<>(result);
    }

    @Override
    public List<CommentForCommentsListDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO) {
        return cr.findByUserId(userForSelfInfoDTO.getId())
                .stream().map(cm::toCommentForCommentsListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentForCommentsListDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO, Pageable pageable) {
        return cr.findByUserId(userForSelfInfoDTO.getId(), pageable).map(cm::toCommentForCommentsListDTO);
    }

    @Override
    public List<CommentForMovieDTO> findByMovie(MovieFullDTO movieFullDTO) {
        return cr.findByMovieId(movieFullDTO.getId())
                .stream().map(cm::toCommentForMovieDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentForMovieDTO> findByMovie(MovieFullDTO movieFullDTO, Pageable pageable) {
        return cr.findByMovieId(movieFullDTO.getId(), pageable).map(cm::toCommentForMovieDTO);
    }

    @Override
    public List<CommentForReportedListDTO> findByReportedTrue() {
        return cr.findByReportedTrue().stream().map(cm::toCommentForReportedListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<CommentForReportedListDTO> findByReportedTrue(Pageable pageable) {
        return cr.findByReportedTrue(pageable).map(cm::toCommentForReportedListDTO);
    }

    @Override
    public void delete(CommentSimpleDTO comment) {
        cr.deleteById(comment.getId());
    }

    @Override
    public void deleteByMovie(MovieSimpleDTO movie) {
        cr.deleteByMovieId(movie.getId());
    }

    @Override
    public void deleteByUser(UserSimpleDTO user) {
        cr.deleteByUserId(user.getId());
    }

    @Override
    public void deleteAll() {
        cr.deleteAll();
    }
}
