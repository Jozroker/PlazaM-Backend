package com.site.plazam.repository;

import com.site.plazam.domain.MoviePicture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePictureRepository extends MongoRepository<MoviePicture, String> {
}
