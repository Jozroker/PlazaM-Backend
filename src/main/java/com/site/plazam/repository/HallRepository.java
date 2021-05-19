package com.site.plazam.repository;

import com.site.plazam.domain.Cinema;
import com.site.plazam.domain.Technology;
import com.site.plazam.domain.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends MongoRepository<Hall, String> {

    List<Hall> findByCinema(Cinema cinema);

    List<Hall> findByTechnology(Technology technology);
}
