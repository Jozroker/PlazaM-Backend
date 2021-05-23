package com.site.plazam.repository;

import com.site.plazam.domain.Day;
import com.site.plazam.domain.Seance;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeanceRepository extends MongoRepository<Seance, String> {

    List<Seance> findByHallId(String id);

    List<Seance> findByMovieId(String id);

    List<Seance> findByDaysContains(Day day);

//    @Query(value = "{" +
//            "$and: [" +
//            "{'date_from':/?0/}," +
//            "{'surname.pl':/?1/}" +
//            "]" +
//            "}")
//    List<Seance> findByDate(LocalDate currentDate);

    List<Seance> findByDateFromBeforeAndDateToAfter(LocalDate date,
                                                    LocalDate date2);

    List<Seance> findByDateFromBeforeAndDateToAfterAndHallIdIsIn(LocalDate date,
                                                                 LocalDate date2,
                                                                 List<String> hallIds);

    Page<Seance> findByHallId(String id, Pageable pageable);

    Page<Seance> findByDateFromBeforeAndDateToAfter(LocalDate date,
                                                    LocalDate date2,
                                                    Pageable pageable);

    Page<Seance> findByDateFromBeforeAndDateToAfterAndHallIdIsIn(LocalDate date,
                                                                 LocalDate date2,
                                                                 List<String> hallIds,
                                                                 Pageable pageable);

    @NotNull
    Page<Seance> findAll(@NotNull Pageable pageable);
}
