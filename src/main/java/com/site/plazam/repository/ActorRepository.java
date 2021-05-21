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
            "{'first_name.en':/?0/}," +
            "{'first_name.ua':/?0/}," +
            "{'first_name.pl':/?0/}," +
            "{'last_name.en':/?1/}," +
            "{'last_name.ua':/?1/}," +
            "{'last_name.pl':/?1/}" +
            "]" +
            "}")
    List<Actor> findByFirstNameOrLastName(String firstName, String lastName);
}
