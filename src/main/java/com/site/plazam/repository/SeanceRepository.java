package com.site.plazam.repository;

import com.site.plazam.domain.Day;
import com.site.plazam.domain.Seance;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

//    List<Seance> findByDateFromBeforeAndDateToAfter(LocalDate date,
//                                                    LocalDate date2);

    @Query("{$and: [" +
            "{'dateFrom' : { $lt: ?0 }}," +
            "{'dateTo' : { $gt: ?1 }}" +
            "{'hallId' : { $in: ?2 }}" +
            "]}")
    List<Seance> findByDateFromBeforeAndDateToAfterAndHallIdIsIn(LocalDate start,
                                                                 LocalDate end,
                                                                 List<String> hallIds);

    void deleteByDateToBefore(LocalDate date);

    @Query("{$and: [" +
            "{'dateFrom' : { $lte: ?0 }}," +
            "{'dateTo' : { $gte: ?1 }}" +
            "{'hallId' : { $in: ?2 }}" +
            "]}")
    List<Seance> findByDateFromBeforeEqualsAndDateToAfterEqualsAndHallIdIsIn(LocalDate start,
                                                                             LocalDate end,
                                                                             List<String> hallIds);

    Page<Seance> findByHallId(String id, Pageable pageable);

    @Query("{$and: [" +
            "{'dateFrom' : { $lt: ?0 }}," +
            "{'dateTo' : { $gt: ?1 }}" +
            "]}")
    Page<Seance> findByDateFromBeforeAndDateToAfter(LocalDate start,
                                                    LocalDate end,
                                                    Pageable pageable);

    @Query("{$and: [" +
            "{'dateFrom' : { $lt: ?0 }}," +
            "{'dateTo' : { $gt: ?1 }}" +
            "{'hallId' : { $in: ?2 }}" +
            "]}")
    Page<Seance> findByDateFromBeforeAndDateToAfterAndHallIdIsIn(LocalDate start,
                                                                 LocalDate end,
                                                                 List<String> hallIds,
                                                                 Pageable pageable);

    @Query("{$or: [" +
            "{'startSeance' : { $gte: ?0, $lte: ?1 }}," +
            "{'endSeance' : { $gte: ?2, $lte: ?3 }}" +

//            "{$and: [" +
//            "{'startSeance': {$gte: ?0}}," +
//            "{'startSeance': {$lte: ?1}}" +
//            "]}," +
//            "{$and: [" +
//            "{'endSeance': {$gte: ?0}}," +
//            "{'endSeance': {$lte: ?1}}" +
//            "]}" +

            "]}")
    List<Seance> findByStartSeanceBetweenOrEndSeanceBetween(LocalDateTime start1,
                                                            LocalDateTime end1,
                                                            LocalDateTime start2,
                                                            LocalDateTime end2);

    @Query("{$and: [" +
            "{'startSeance' : { $lt: ?0 }}," +
            "{'endSeance' : { $gt: ?1 }}" +
            "]}")
    List<Seance> findByStartSeanceBeforeAndEndSeanceAfter(LocalDateTime start,
                                                          LocalDateTime end);

    @Query("{$or: [" +
            "{'dateFrom' : { $gte: ?0, $lte: ?1 }}," +
            "{'dateTo' : { $gte: ?2, $lte: ?3 }}" +
            "]}")
    List<Seance> findByDateFromBetweenOrDateToBetween(LocalDate start1,
                                                      LocalDate end1,
                                                      LocalDate start2,
                                                      LocalDate end2);

    @Query("{$and: [" +
            "{'dateFrom' : { $lt: ?0 }}," +
            "{'dateTo' : { $gt: ?1 }}" +
            "]}")
    List<Seance> findByDateFromBeforeAndDateToAfter(LocalDate start,
                                                    LocalDate end);

    @NotNull
    Page<Seance> findAll(@NotNull Pageable pageable);
}
