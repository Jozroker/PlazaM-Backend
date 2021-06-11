package com.site.plazam.repository;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    @Query(value = "{" +
            "$or: [" +
            "{'full_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'full_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'full_name.pol': {$regex: ?0, $options: 'i'}}" +
            "]" +
            "}")
    List<Movie> findByMovieName(String fullName);

    @Query(value = "{" +
            "$or: [" +
            "{'full_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'full_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'full_name.pol': {$regex: ?0, $options: 'i'}}" +
            "]" +
            "}")
    Page<Movie> findByMovieName(String fullName, Pageable pageable);

    @NotNull
    Page<Movie> findAll(@NotNull Pageable pageable);

    @Query("{'genres': {$in: ?0}}")
    Page<Movie> findAllByGenresMatches(List<Genre> genres, Pageable pageable);

    List<Movie> findByGenresIsContaining(List<Genre> genres);

    Page<Movie> findByGenresIsContaining(List<Genre> genres, Pageable pageable);

    List<Movie> findByReleaseDateAfter(LocalDate date);

    List<Movie> findByReleaseDateAfterOrderByReleaseDate(LocalDate date);

    List<Movie> findByReleaseDateBeforeOrderByReleaseDate(LocalDate date);

    List<Movie> findByReleaseDateBefore(LocalDate date);

    @Query("{'releaseDate' : { $gte: ?0, $lte: ?1 } }")
    List<Movie> findByReleaseDateBetween(LocalDate from, LocalDate to);
//    List<Movie> findByReleaseDateGreaterThanEqualAndReleaseDateLessThanEqual(LocalDate from,
//                                                   LocalDate to);

    Page<Movie> findByReleaseDateAfter(LocalDate date, Pageable pageable);

    Page<Movie> findByReleaseDateAfterAndGenresMatches(LocalDate date,
                                                       List<Genre> genres,
                                                       Pageable pageable);

    Page<Movie> findByReleaseDateBefore(LocalDate date, Pageable pageable);

    Page<Movie> findByReleaseDateBeforeAndGenresMatches(LocalDate date,
                                                        List<Genre> genres,
                                                        Pageable pageable);

    @Query("{'releaseDate' : { $gte: ?0, $lte: ?1 } }")
    Page<Movie> findByReleaseDateBetween(LocalDate from, LocalDate to, Pageable pageable);

    @Query("{'releaseDate' : { $gte: ?0, $lte: ?1 }, 'genres': {$in: ?2} }")
    Page<Movie> findByReleaseDateBetweenAndGenresMatches(LocalDate from,
                                                         LocalDate to,
                                                         List<Genre> genres,
                                                         Pageable pageable);

    List<Movie> findByActorIdsContains(String id);

//    @Query(value = "{}")
//    void updateMovieTechnologyListById(String id,
//                                       List<Technology> technologies);
}
