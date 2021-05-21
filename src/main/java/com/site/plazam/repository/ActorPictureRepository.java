package com.site.plazam.repository;

import com.site.plazam.domain.ActorPicture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorPictureRepository extends MongoRepository<ActorPicture, String> {
}