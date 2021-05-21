package com.site.plazam.repository;

import com.site.plazam.domain.UserPicture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPictureRepository extends MongoRepository<UserPicture, String> {
}