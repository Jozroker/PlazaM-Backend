package com.site.plazam.repository;

import com.site.plazam.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByMovieId(String id);

    List<Comment> findByUserId(String id);

    List<Comment> findByUserIdAndMovieId(String userId, String movieId);

//    @Query(value = "{'reported':true}")
List<Comment> findByReportedTrue();

    //    @Query(value = "{'reported':true}")
    Page<Comment> findByReportedTrue(Pageable pageable);

    Page<Comment> findByMovieId(String id, Pageable pageable);

    Page<Comment> findByUserId(String id, Pageable pageable);

    Page<Comment> findByUserIdAndMovieId(String userId, String movieId,
                                         Pageable pageable);
}
