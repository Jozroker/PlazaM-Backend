package com.site.plazam.repository;

import com.site.plazam.domain.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'first_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'first_name.pol': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.eng': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.ukr': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.pol': {$regex: ?1, $options: 'i'}}" +
            "{'first_name.eng': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.ukr': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.pol': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.pol': {$regex: ?0, $options: 'i'}}" +
            "]" +
            "}")
    List<Actor> findByFirstNameOrLastName(String firstName, String lastName);
}
