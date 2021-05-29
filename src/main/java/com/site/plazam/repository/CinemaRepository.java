package com.site.plazam.repository;

import com.site.plazam.domain.Cinema;
import com.site.plazam.domain.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends MongoRepository<Cinema, String> {

    @Query(value = "{" +
            "$and: [" +
            "{'country': ?0}," +
            "{$or: [" +
            "{'city.eng': ?1}," +
            "{'city.ukr': ?1}," +
            "{'city.pol': ?1}" +
            "]}" +
            "]" +
            "}")
    List<Cinema> findFirstByCountryAndCity(Country country, String city);
}
