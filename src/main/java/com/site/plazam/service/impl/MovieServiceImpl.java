package com.site.plazam.service.impl;

import com.site.plazam.domain.Genre;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.repository.MovieRepository;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.SeanceService;
import com.site.plazam.service.mapper.MovieMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository mr;

    private final MovieMapper mm;

    private final SeanceService ss;

    public MovieServiceImpl(MovieRepository movieRepository,
                            MovieMapper movieMapper, SeanceService seanceService) {
        this.mr = movieRepository;
        this.mm = movieMapper;
        this.ss = seanceService;
    }

    @Override
    public MovieFullDTO save(MovieCreateDTO movieCreateDTO) {
        return mm.toMovieFullDTO(mr.save(mm.toEntity(movieCreateDTO)));
    }

    @Override
    public MovieForComingSoonDTO findMovieForComingSoonById(String id) {
        return mr.findById(id).map(mm::toMovieForComingSoonDTO).orElse(null);
    }

    @Override
    public MovieForCommentDTO findMovieForCommentById(String id) {
        return mr.findById(id).map(mm::toMovieForCommentDTO).orElse(null);
    }

    @Override
    public MovieForHomeSliderDTO findMovieForHomeSliderById(String id) {
        return mr.findById(id).map(mm::toMovieForHomeSliderDTO).orElse(null);
    }

    @Override
    public MovieForMoviesListDTO findMovieForMoviesListById(String id) {
        return mr.findById(id).map(mm::toMovieForMoviesListDTO).orElse(null);
    }

    @Override
    public MovieForSeanceDTO findMovieForSeanceById(String id) {
        return mr.findById(id).map(mm::toMovieForSeanceDTO).orElse(null);
    }

    @Override
    public MovieForTicketDTO findMovieForTicketById(String id) {
        return mr.findById(id).map(mm::toMovieForTicketDTO).orElse(null);
    }

    @Override
    public MovieFullDTO findMovieFullById(String id) {
        return mr.findById(id).map(mm::toMovieFullDTO).orElse(null);
    }

    @Override
    public List<MovieForComingSoonDTO> findMovieForComingSoonAll() {
        return mr.findAll().stream().map(mm::toMovieForComingSoonDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForHomeSliderDTO> findMovieForHomeSliderAll() {
        return mr.findAll().stream().map(mm::toMovieForHomeSliderDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForMoviesListDTO> findMovieForMoviesListAll() {
        return mr.findAll().stream().map(mm::toMovieForMoviesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForSeanceDTO> findMovieForSeanceByGenresIsContaining(List<Genre> genres) {
        return mr.findByGenresIsContaining(genres).stream().map(mm::toMovieForSeanceDTO).collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public List<MovieForHomeSliderDTO> findMovieForHomeSliderByLastSeances(LocalDate currentDate) {
//        Day currentDay = Day.valueOf(currentDate.getDayOfWeek().toString());
//        return ss.findByDateFromBeforeAndDateToAfter(currentDate, currentDate)
//                .stream()
//                .filter(seance -> seance.getDays().contains(currentDay))
//                .sorted(new SeanceForSeancesListByDateComparator())
//                .limit(7).map(SeanceForSeancesListDTO::getMovie)
//                .map(mm::);
//    }

    @Override
    public Page<MovieForSeanceDTO> findMovieForSeanceByGenresIsContaining(List<Genre> genres, Pageable pageable) {
        return mr.findByGenresIsContaining(genres, pageable).map(mm::toMovieForSeanceDTO);
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListAll(Pageable pageable) {
        return mr.findAll(pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public List<MovieForMoviesListDTO> findMovieForMoviesListByMovieName(String fullName) {
        return mr.findByMovieName(fullName).stream().map(mm::toMovieForMoviesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForResultListDTO> findMovieForResultListByMovieName(String fullName) {
        return mr.findByMovieName(fullName).stream().map(mm::toMovieForResultListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByMovieName(String fullName, Pageable pageable) {
        return mr.findByMovieName(fullName, pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public List<MovieForHomeSliderDTO> findMovieForHomeSlideByReleaseDateAfter(LocalDate date) {
        return mr.findByReleaseDateAfter(date).stream().map(mm::toMovieForHomeSliderDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateAfter(LocalDate date) {
        return mr.findByReleaseDateAfter(date).stream().map(mm::toMovieForComingSoonDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateBefore(LocalDate date) {
        return mr.findByReleaseDateBefore(date).stream().map(mm::toMovieForComingSoonDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateBetween(LocalDate from, LocalDate to) {
        return mr.findByReleaseDateBetween(from, to).stream().map(mm::toMovieForComingSoonDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateAfter(LocalDate date) {
        return mr.findByReleaseDateAfter(date).stream().map(mm::toMovieForMoviesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBefore(LocalDate date) {
        return mr.findByReleaseDateBefore(date).stream().map(mm::toMovieForMoviesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBetween(LocalDate from, LocalDate to) {
        return mr.findByReleaseDateBetween(from, to).stream().map(mm::toMovieForMoviesListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateAfter(LocalDate date, Pageable pageable) {
        return mr.findByReleaseDateAfter(date, pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBefore(LocalDate date, Pageable pageable) {
        return mr.findByReleaseDateBefore(date, pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBetween(LocalDate from, LocalDate to, Pageable pageable) {
        return mr.findByReleaseDateBetween(from, to, pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateAfterOrderByReleaseDate(LocalDate date) {
        return mr.findByReleaseDateAfterOrderByReleaseDate(date)
                .stream()
                .limit(7)
                .map(mm::toMovieForComingSoonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(MovieSimpleDTO movie) {
        mr.deleteById(movie.getId());
    }

    @Override
    public void deleteAll() {
        mr.deleteAll();
    }
}
