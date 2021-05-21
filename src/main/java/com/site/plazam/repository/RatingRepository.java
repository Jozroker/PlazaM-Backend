package com.site.plazam.repository;

import com.site.plazam.domain.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findByMovieId(String id);

    List<Rating> findByUserId(String id);

    Optional<Rating> findByUserIdAndMovieId(String userId, String movieId);

//    Integer countByMovieId(String id);
}
