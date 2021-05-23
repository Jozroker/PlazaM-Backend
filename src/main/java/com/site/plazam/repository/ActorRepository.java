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
            "{'first_name.en':/?0/i}," +
            "{'first_name.ua':/?0/i}," +
            "{'first_name.pl':/?0/i}," +
            "{'last_name.en':/?1/i}," +
            "{'last_name.ua':/?1/i}," +
            "{'last_name.pl':/?1/i}" +
            "]" +
            "}")
    List<Actor> findByFirstNameOrLastName(String firstName, String lastName);
}
