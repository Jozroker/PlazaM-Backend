package com.site.plazam.repository;

import com.site.plazam.domain.Hall;
import com.site.plazam.domain.Technology;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends MongoRepository<Hall, String> {

    List<Hall> findByCinemaId(String cinemaId);

    List<Hall> findByTechnology(Technology technology);

    List<Hall> findByTechnologyIsIn(List<Technology> technologies);
}
