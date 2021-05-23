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
            "{'full_name.en':/?0/i}," +
            "{'full_name.ua':/?0/i}," +
            "{'full_name.pl':/?0/i}" +
            "]" +
            "}")
    List<Movie> findByMovieName(String fullName);

    @Query(value = "{" +
            "$or: [" +
            "{'full_name.en':/?0/i}," +
            "{'full_name.ua':/?0/i}," +
            "{'full_name.pl':/?0/i}" +
            "]" +
            "}")
    Page<Movie> findByMovieName(String fullName, Pageable pageable);

    @NotNull
    Page<Movie> findAll(@NotNull Pageable pageable);

    List<Movie> findByGenresIsContaining(List<Genre> genres);

    Page<Movie> findByGenresIsContaining(List<Genre> genres, Pageable pageable);

    List<Movie> findByReleaseDateAfter(LocalDate date);

    List<Movie> findByReleaseDateAfterOrderByReleaseDate(LocalDate date);

    List<Movie> findByReleaseDateBefore(LocalDate date);

    List<Movie> findByReleaseDateBetween(LocalDate from, LocalDate to);

    Page<Movie> findByReleaseDateAfter(LocalDate date, Pageable pageable);

    Page<Movie> findByReleaseDateBefore(LocalDate date, Pageable pageable);

    Page<Movie> findByReleaseDateBetween(LocalDate from, LocalDate to, Pageable pageable);

//    @Query(value = "{}")
//    void updateMovieTechnologyListById(String id,
//                                       List<Technology> technologies);
}
