package com.site.plazam.dto.comparator;

import com.site.plazam.dto.MovieForMoviesListDTO;

import java.util.Comparator;

public class MoviesByPopularComparator implements Comparator<MovieForMoviesListDTO> {

    @Override
    public int compare(MovieForMoviesListDTO movie1,
                       MovieForMoviesListDTO movie2) {
        return Double.compare(movie2.getUsersRating(), movie1.getUsersRating());
    }
}
