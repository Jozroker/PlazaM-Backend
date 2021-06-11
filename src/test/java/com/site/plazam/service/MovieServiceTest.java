package com.site.plazam.service;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.Lang;
import com.site.plazam.domain.MPAA;
import com.site.plazam.domain.Technology;
import com.site.plazam.dto.*;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieServiceTest {

    static MovieCreateDTO movieCreateDTO;
    static MovieFullDTO movieFullDTO;
    static List<MovieForMoviesListDTO> movieForMoviesListDTOList;
    static Page<MovieForMoviesListDTO> movieForMoviesListDTOPage;
    static List<MovieForSeanceDTO> movieForSeanceDTOList;
    static ActorForActorListDTO newActor;
    @Autowired
    MovieService movieService;
    @Autowired
    ActorService actorService;

    @Test
    @Order(1)
    void save() {
        ActorCreateDTO actorCreateDTO = new ActorCreateDTO();
        actorCreateDTO.setFirstName(new HashMap<String, String>() {{
            put("eng", "actorchyck");
            put("ukr", "actorchyck");
            put("pol", "actorchyck");
        }});
        actorCreateDTO.setLastName(new HashMap<String, String>() {{
            put("eng", "actorchyck");
            put("ukr", "actorchyck");
            put("pol", "actorchyck");
        }});
        newActor = actorService.save(actorCreateDTO);
        movieCreateDTO = new MovieCreateDTO();
        movieCreateDTO.setActors(com.sun.tools.javac.util.List.of(newActor));
        movieCreateDTO.setDescription(new HashMap<String, String>() {{
            put("eng", "newMovieDesc");
            put("ukr", "newMovieDesc");
            put("pol", "newMovieDesc");
        }});
        movieCreateDTO.setDirectedBy(new HashMap<String, String>() {{
            put("eng", "newMovieDirector");
            put("ukr", "newMovieDirector");
            put("pol", "newMovieDirector");
        }});
        movieCreateDTO.setDuration(153);
        movieCreateDTO.setFullName(new HashMap<String, String>() {{
            put("eng", "Full Name");
            put("ukr", "Full Name");
            put("pol", "Full Name");
        }});
        movieCreateDTO.setGenres(com.sun.tools.javac.util.List.of(Genre.CARTOON, Genre.FANTASY, Genre.SCIENCE_FICTION));
        movieCreateDTO.setImdbRating(5);
        movieCreateDTO.setMovieCountry(new HashMap<String, String>() {{
            put("eng", "China");
            put("ukr", "China");
            put("pol", "China");
        }});
        movieCreateDTO.setMovieLang(Lang.ENG);
        movieCreateDTO.setMpaaRating(MPAA.G);
        movieCreateDTO.setName(new HashMap<String, String>() {{
            put("eng", "Full");
            put("ukr", "Full");
            put("pol", "Full");
        }});
        movieCreateDTO.setSurname(new HashMap<String, String>() {{
            put("eng", "Name");
            put("ukr", "Name");
            put("pol", "Name");
        }});
        movieCreateDTO.setReleaseDate(LocalDate.of(2021, 5, 29));
        movieCreateDTO.setUsersRating(9.1);
        movieFullDTO = movieService.save(movieCreateDTO);
        System.out.println(movieFullDTO.toString());
        System.out.println("==============================");
        Assert.assertNotNull(movieFullDTO.getId());
    }

    @Test
    @Order(2)
    void findById() {
        movieFullDTO = movieService.findMovieFullById(movieFullDTO.getId());
        System.out.println(movieFullDTO.toString());
        System.out.println("==============================");
        Assert.assertNotNull(movieFullDTO);
    }

    @Test
    @Order(3)
    void findAll() {
        movieForMoviesListDTOList = movieService.findMovieForMoviesListAll();
        System.out.println("[");
        movieForMoviesListDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("==============================");
        Assert.assertFalse(movieForMoviesListDTOList.isEmpty());
    }

    @Test
    @Order(4)
    void findAllPage() {
        for (int i = 0; i < 5; i++) {
            movieForMoviesListDTOPage =
                    movieService.findMovieForMoviesListAll(PageRequest.of(i,
                            4));
            if (movieForMoviesListDTOPage.hasContent()) {
                System.out.println("Page" + (i + 1) + "[");
                movieForMoviesListDTOPage.forEach(movie -> System.out.println("    " +
                        " " + movie.toString()));
                System.out.println("]");
            } else {
                break;
            }
        }
        System.out.println("==============================");
//        Assert.assertTrue(movieForMoviesListDTOPage.hasContent());
    }

    @Test
    @Order(5)
    void findByGenresContains() {
        movieForSeanceDTOList =
                movieService.findMovieForSeanceByGenresIsContaining(com.sun.tools.javac.util.List.of(Genre.CARTOON));
        System.out.println("[");
        movieForSeanceDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("------------------------------");
        movieForSeanceDTOList =
                movieService.findMovieForSeanceByGenresIsContaining(com.sun.tools.javac.util.List.of(Genre.CARTOON, Genre.ACTION));
        System.out.println("[");
        movieForSeanceDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("==============================");
        Assert.assertFalse(movieForSeanceDTOList.isEmpty());
    }

    @Test
    @Order(6)
    void findByMovieName() {
        movieForMoviesListDTOList =
                movieService.findMovieForMoviesListByMovieName("Full Name");
        System.out.println("[");
        movieForMoviesListDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("------------------------------");
        movieForMoviesListDTOList =
                movieService.findMovieForMoviesListByMovieName("movie");
        System.out.println("[");
        movieForMoviesListDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("==============================");
        Assert.assertFalse(movieForMoviesListDTOList.isEmpty());
    }

    @Test
    @Order(7)
    void findByReleaseDateAfter() {
        movieForMoviesListDTOList =
                movieService.findMovieForMoviesListByReleaseDateAfter(LocalDate.now());
        System.out.println("[");
        movieForMoviesListDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("==============================");
        Assert.assertFalse(movieForMoviesListDTOList.isEmpty());
    }

    @Test
    @Order(8)
    void findByReleaseDateBetween() {
        movieForMoviesListDTOList =
//                movieService.findMovieForMoviesListByReleaseDateBetween(LocalDate.of(2021, 5, 28), LocalDate.of(2021, 6, 1));
                movieService.findMovieForMoviesListByReleaseDateBetween(LocalDate.of(2021, 5, 27), LocalDate.of(2021, 5, 28));
//                  movieService.findMovieForMoviesListByReleaseDateBefore(LocalDate.of(2021, 5, 28));
        movieForMoviesListDTOList.forEach(movie -> System.out.println("     " + movie.toString()));
        System.out.println("]");
        System.out.println("==============================");
        Assert.assertFalse(movieForMoviesListDTOList.isEmpty());
    }

    @Test
    @Order(9)
    void update() {
        movieCreateDTO.setId(movieFullDTO.getId());
        movieCreateDTO.setUsersRating(1);
        movieCreateDTO.setImdbRating(1);
        movieFullDTO = movieService.save(movieCreateDTO);
        System.out.println(movieFullDTO.toString());
        System.out.println("==============================");
        Assert.assertEquals(1, movieFullDTO.getUsersRating(), 0.1);
        Assert.assertEquals(1, movieFullDTO.getImdbRating(), 0.1);
    }

    @Test
    @Order(10)
    void deleteActorsFromMovies() {
        System.out.println(movieFullDTO.getActors());
        System.out.println("------------------------------");
        actorService.delete(newActor);
        movieFullDTO = movieService.findMovieFullById(movieFullDTO.getId());
        System.out.println(movieFullDTO.getActors());
        System.out.println("==============================");
    }

    @Test
    @Order(11)
    void addAvailableTech() {
        MovieForMoviesListDTO movie =
                movieService.findMovieForMoviesListById(movieFullDTO.getId());
        System.out.println(movie.getAvailableTechnologies());
        System.out.println("------------------------------");
        movieService.addAvailableTechnology(movie, Technology._RM_PLUS);
        movie = movieService.findMovieForMoviesListById(movie.getId());
        System.out.println(movie.getAvailableTechnologies());
        System.out.println("==============================");
        Assert.assertFalse(movie.getAvailableTechnologies().isEmpty());
    }

    @Test
    @Order(12)
    void removeAvailableTech() {
        MovieForMoviesListDTO movie =
                movieService.findMovieForMoviesListById(movieFullDTO.getId());
        System.out.println(movie.getAvailableTechnologies());
        System.out.println("------------------------------");
        movieService.removeAvailableTechnology(movie, Technology._RM_PLUS);
        movie = movieService.findMovieForMoviesListById(movie.getId());
        System.out.println(movie.getAvailableTechnologies());
        System.out.println("==============================");
        Assert.assertFalse(movie.getAvailableTechnologies().contains(Technology._RM_PLUS));
    }

    @Test
    @Order(13)
    void delete() {
        movieService.delete(movieFullDTO);
        movieFullDTO = movieService.findMovieFullById(movieFullDTO.getId());
        Assert.assertNull(movieFullDTO);
    }

//    @Test
//    void excludedTest() {
//        movieFullDTO = movieService.findMovieFullById("60ad46392302cc6de0d9ed15");
//        System.out.println(movieFullDTO.getActors());
//    }
}
