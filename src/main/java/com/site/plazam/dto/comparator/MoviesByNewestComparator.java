package com.site.plazam.dto.comparator;

import com.site.plazam.dto.MovieForMoviesListDTO;

import java.util.Comparator;

public class MoviesByNewestComparator implements Comparator<MovieForMoviesListDTO> {

    @Override
    public int compare(MovieForMoviesListDTO movie1,
                       MovieForMoviesListDTO movie2) {
        return movie2.getReleaseDate().compareTo(movie1.getReleaseDate());
    }
}
