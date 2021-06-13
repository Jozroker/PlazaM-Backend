package com.site.plazam.service.impl;

import com.site.plazam.domain.*;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.repository.MovieRepository;
import com.site.plazam.service.*;
import com.site.plazam.service.mapper.MovieMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository mr;

    private final MovieMapper mm;

    private final MongoTemplate mt;

    @Lazy
    private final PictureService ps;

    @Lazy
    private final CommentService cs;

    @Lazy
    private final RatingService rs;

    @Lazy
    private final UserService us;

    public MovieServiceImpl(MovieRepository movieRepository,
                            MovieMapper movieMapper,
                            @Lazy PictureService pictureService,
                            @Lazy CommentService commentService,
                            @Lazy RatingService ratingService,
                            MongoTemplate mongoTemplate,
                            @Lazy UserService userService) {
        this.mr = movieRepository;
        this.mm = movieMapper;
        this.ps = pictureService;
        this.cs = commentService;
        this.rs = ratingService;
        this.mt = mongoTemplate;
        this.us = userService;
    }

    @Override
    @Transactional
    public MovieFullDTO save(MovieCreateDTO movieCreateDTO) {
        if (movieCreateDTO.getId() != null) {
            MovieFullDTO movie = findMovieFullById(movieCreateDTO.getId());
            if (movieCreateDTO.getWidePicture() != null) {
                if (movieCreateDTO.getWidePicture().getId() == null && movie.getWidePicture() != null) {
                    ps.delete(movie.getWidePicture(), MoviePicture.class);
                    movieCreateDTO.setWidePicture(ps.save(movieCreateDTO.getWidePicture(), MoviePicture.class));
                }
            }
            if (movieCreateDTO.getPosterPicture() != null) {
                if (movieCreateDTO.getPosterPicture().getId() == null && movie.getPosterPicture() != null) {
                    ps.delete(movie.getPosterPicture(), MoviePicture.class);
                    movieCreateDTO.setPosterPicture(ps.save(movieCreateDTO.getPosterPicture(),
                            MoviePicture.class));
                }
            }
            List<PictureDTO> actualPictures = movieCreateDTO.getGalleryPictures().stream().map(picture -> {
                if (picture.getId() == null) {
                    return ps.save(picture, MoviePicture.class);
                }
                return picture;
            }).collect(Collectors.toList());
            movie.getGalleryPictures().stream().filter(actualPictures::contains)
                    .forEach(picture -> ps.delete(picture, MoviePicture.class));
        } else {
            if (movieCreateDTO.getWidePicture() != null) {
                movieCreateDTO.setWidePicture(ps.save(movieCreateDTO.getWidePicture(),
                        MoviePicture.class));
            }
            if (movieCreateDTO.getPosterPicture() != null) {
                movieCreateDTO.setPosterPicture(ps.save(movieCreateDTO.getPosterPicture(),
                        MoviePicture.class));
            }
            movieCreateDTO.getGalleryPictures().forEach(picture ->
                    picture.setId(ps.save(picture, MoviePicture.class).getId()));
//            for (PictureDTO galleryPicture : movieCreateDTO.getGalleryPictures()) {
//                galleryPicture = ps.save(galleryPicture, MoviePicture.class);
//            }
        }
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
    public MovieCreateDTO findMovieCreateById(String id) {
        return mr.findById(id).map(mm::toMovieCreateDTO).orElse(null);
    }

    @Override
    public MovieForResultListDTO findMovieForResultListById(String id) {
        return mr.findById(id).map(mm::toMovieForResultListDTO).orElse(null);
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
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateAfterAndGenresMatches(LocalDate date, List<Genre> genres, Pageable pageable) {
        return mr.findByReleaseDateAfterAndGenresIn(date, genres,
                pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBeforeAndGenresMatches(LocalDate date, List<Genre> genres, Pageable pageable) {
        return mr.findByReleaseDateBeforeAndGenresIn(date, genres,
                pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBetweenAndGenresMatches(LocalDate from, LocalDate to, List<Genre> genres, Pageable pageable) {
        return mr.findByReleaseDateBetweenAndGenresIn(from, to,
                genres, pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public Page<MovieForMoviesListDTO> findAllByGenresMatches(List<Genre> genres, Pageable pageable) {
        return mr.findAllByGenresIn(genres, pageable).map(mm::toMovieForMoviesListDTO);
    }

    @Override
    public List<MovieForHomeSliderDTO> findMovieForHomeSliderByReleaseDateBeforeOrderByReleaseDate(LocalDate date) {
        //        Collections.reverse(movies);
        return mr.findByReleaseDateBeforeOrderByReleaseDateDesc(date)
                .stream()
                .filter(movie -> movie.getReleaseDate().isBefore(LocalDate.now()) ||
                        movie.getReleaseDate().isEqual(LocalDate.now()))
                .limit(5)
                .map(mm::toMovieForHomeSliderDTO)
                .collect(Collectors.toList());
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
    public void removeActorFromMovies(ActorSimpleDTO actor) {
        List<MovieFullDTO> movies = mr.findByActorIdsContains(actor.getId())
                .stream().map(mm::toMovieFullDTO).collect(Collectors.toList());
        for (MovieFullDTO movie : movies) {
            Query query = new Query(Criteria.where("id").is(movie.getId()));
            List<String> newActorList =
                    movie.getActors().stream()
                            .map(ActorSimpleDTO::getId)
                            .filter(id -> !id.equals(actor.getId()))
                            .collect(Collectors.toList());
            Update update = new Update().set("actorIds", newActorList);
            mt.findAndModify(query, update, User.class);
        }
    }

    @Override
    public void addAvailableTechnology(MovieSimpleDTO movie,
                                       Technology technology) {
        Query query = new Query(Criteria.where("id").is(movie.getId()));
        Set<Technology> newTechnologyList =
                findMovieForMoviesListById(movie.getId()).getAvailableTechnologies();
        newTechnologyList.add(technology);
        Update update = new Update().set("availableTechnologies",
                newTechnologyList);
        mt.findAndModify(query, update, Movie.class);
    }

    @Override
    public void removeAvailableTechnology(MovieSimpleDTO movie,
                                          Technology technology) {
        Query query = new Query(Criteria.where("id").is(movie.getId()));
        Set<Technology> newTechnologyList =
                findMovieForMoviesListById(movie.getId()).getAvailableTechnologies();
        newTechnologyList.remove(technology);
        Update update = new Update().set("availableTechnologies",
                newTechnologyList);
        mt.findAndModify(query, update, Movie.class);
    }

    @Override
    @Transactional
    public void delete(MovieSimpleDTO movie) {
        MovieFullDTO movieSearch = findMovieFullById(movie.getId());
        if (movieSearch.getWidePicture() != null) {
            ps.delete(movieSearch.getWidePicture(), MoviePicture.class);
        }
        if (movieSearch.getPosterPicture() != null) {
            ps.delete(movieSearch.getPosterPicture(), MoviePicture.class);
        }
        movieSearch.getGalleryPictures().forEach(picture -> ps.delete(picture, MoviePicture.class));
        cs.deleteByMovie(movie);
        rs.deleteByMovie(movie);
        us.removeMovieFromFavouriteMoviesList(movie);
        us.removeMovieFromViewedMoviesList(movie);
        us.removeMovieFromWaitMoviesList(movie);
        mr.deleteById(movie.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        ps.deleteAll(MoviePicture.class);
        cs.deleteAll();
        rs.deleteAll();
        Query query = new Query();
        Update update = new Update().set("favouriteMovieIds", new ArrayList<>())
                .set("viewedMovieIds", new ArrayList<>())
                .set("waitMovieIds", new ArrayList<>());
        mt.findAndModify(query, update, Movie.class);
        mr.deleteAll();
    }
}
