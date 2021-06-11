package com.site.plazam.dto.comparator;

import com.site.plazam.dto.MovieForMoviesListDTO;

import java.time.LocalDate;
import java.util.Comparator;

public class MoviesByNewestComparator implements Comparator<MovieForMoviesListDTO> {

    @Override
    public int compare(MovieForMoviesListDTO movie1,
                       MovieForMoviesListDTO movie2) {
        if (movie1.getReleaseDate().isAfter(LocalDate.now())) {
            return 1;
        } else if (movie2.getReleaseDate().isAfter(LocalDate.now())) {
            return -1;
        }
        return movie1.getReleaseDate().compareTo(movie2.getReleaseDate());
    }
}
