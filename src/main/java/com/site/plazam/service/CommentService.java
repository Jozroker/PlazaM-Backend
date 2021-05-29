package com.site.plazam.service;

import com.site.plazam.domain.Role;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CommentSimpleDTO;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentForMovieDTO save(CommentCreateDTO commentCreateDTO);

    CommentForCommentsListDTO updateCommentText(CommentForCommentsListDTO commentForCommentsListDTO);


    CommentForCommentsListDTO findCommentForCommentsListById(String id);

    CommentForMovieDTO findCommentForMovieById(String id);

    CommentForReportedListDTO findCommentForReportedListById(String id);

    List<CommentForCommentsListDTO> findCommentForCommentsListAll();

    Page<CommentForCommentsListDTO> findCommentForCommentsListAll(Pageable pageable);

    List<CommentForMovieDTO> findCommentForMovieAll();

    Page<CommentForMovieDTO> findCommentForMovieAll(Pageable pageable);

    List<CommentForReportedListDTO> findCommentForReportedListAll();

    Page<CommentForReportedListDTO> findCommentForReportedListAll(Pageable pageable);


    List<CommentForReportedListDTO> findCommentForReportedListByUserRole(Role role);

    Page<CommentForReportedListDTO> findCommentForReportedListByUserRole(Role role, Pageable pageable);

    List<CommentForReportedListDTO> findCommentForReportedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);

    Page<CommentForReportedListDTO> findCommentForReportedListByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username, Pageable pageable);


    List<CommentForCommentsListDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO);

    Page<CommentForCommentsListDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO, Pageable pageable);

    List<CommentForMovieDTO> findByMovie(MovieFullDTO movieFullDTO);

    Page<CommentForMovieDTO> findByMovie(MovieFullDTO movieFullDTO,
                                         Pageable pageable);

    List<CommentForReportedListDTO> findByReportedTrue();

    Page<CommentForReportedListDTO> findByReportedTrue(Pageable pageable);

    void delete(CommentSimpleDTO comment);

    void deleteByMovie(MovieSimpleDTO movie);

    void deleteByUser(UserSimpleDTO user);

    void deleteAll();
}
