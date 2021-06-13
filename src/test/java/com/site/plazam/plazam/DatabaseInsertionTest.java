package com.site.plazam.plazam;

import com.site.plazam.domain.*;
import com.site.plazam.repository.*;
import com.site.plazam.service.*;
import com.site.plazam.service.mapper.*;
import com.sun.tools.javac.util.List;
import org.bson.types.Binary;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseInsertionTest {

    @Autowired
    UserPictureRepository userPictureRepository;
    @Autowired
    MoviePictureRepository moviePictureRepository;
    @Autowired
    ActorPictureRepository actorPictureRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    SeanceRepository seanceRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PictureService pictureService;
    @Autowired
    ActorService actorService;
    @Autowired
    CinemaService cinemaService;
    @Autowired
    CommentService commentService;
    @Autowired
    HallService hallService;
    @Autowired
    MessageService messageService;
    @Autowired
    MovieService movieService;
    @Autowired
    RatingService ratingService;
    @Autowired
    SeanceService seanceService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;

    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    ActorMapper actorMapper;
    @Autowired
    CinemaMapper cinemaMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    HallMapper hallMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    RatingMapper ratingMapper;
    @Autowired
    SeanceMapper seanceMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    UserMapper userMapper;

    byte[] byteArray = new byte[10];
    Cinema cinema1, cinema2;
    Message message1, message2;
    Hall hall11, hall12, hall21, hall22;
    ActorPicture actorPicture1, actorPicture2, actorPicture3;
    MoviePicture movieWide1, moviePoster1, movieWide2, moviePoster3;
    MoviePicture gallery1, gallery2, gallery3;
    Actor actor1, actor2, actor3, actor4;
    UserPicture userPicture1, userPicture2;
    Movie movie1, movie2, movie3, movie4, movie5;
    Seance s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;
    Ticket ticket1, ticket2, ticket3;
    User user, worker, admin;
    Comment comment1, comment2, comment3;
    Rating rating1, rating2, rating3;

    @Test
    @Order(1)
    public void insertTest() {
        byteArray[0] = Byte.MAX_VALUE;

        actorPicture1 = new ActorPicture(new Binary(byteArray));
        actorPicture2 = new ActorPicture(new Binary(byteArray));
        actorPicture3 = new ActorPicture(new Binary(byteArray));
        movieWide1 = new MoviePicture(new Binary(byteArray));
        movieWide2 = new MoviePicture(new Binary(byteArray));
        moviePoster1 = new MoviePicture(new Binary(byteArray));
        moviePoster3 = new MoviePicture(new Binary(byteArray));
        gallery1 = new MoviePicture(new Binary(byteArray));
        gallery2 = new MoviePicture(new Binary(byteArray));
        gallery3 = new MoviePicture(new Binary(byteArray));
        userPicture1 = new UserPicture(new Binary(byteArray));
        userPicture2 = new UserPicture(new Binary(byteArray));
        actorPicture1 = actorPictureRepository.save(actorPicture1);
        actorPicture2 = actorPictureRepository.save(actorPicture2);
        actorPicture3 = actorPictureRepository.save(actorPicture3);
        movieWide1 = moviePictureRepository.save(movieWide1);
        movieWide2 = moviePictureRepository.save(movieWide2);
        moviePoster1 = moviePictureRepository.save(moviePoster1);
        moviePoster3 = moviePictureRepository.save(moviePoster3);
        userPicture1 = userPictureRepository.save(userPicture1);
        userPicture2 = userPictureRepository.save(userPicture2);
        gallery1 = moviePictureRepository.save(gallery1);
        gallery2 = moviePictureRepository.save(gallery2);
        gallery3 = moviePictureRepository.save(gallery3);

        cinema1 = new Cinema();
        cinema1.setName(new HashMap<String, String>() {{
            put("eng",
                    "Cinema1");
            put(
                    "ukr", "Кінотеатр1");
            put("pol", "Cinema1");
        }});
        cinema1.setCountry(Country.UKRAINE);
        cinema1.setCity(new HashMap<String, String>() {{
            put("eng",
                    "City1");
            put(
                    "ukr", "Місто1");
            put("pol", "City1");
        }});
        cinema1.setStreet(new HashMap<String, String>() {{
            put("eng",
                    "Street1");
            put(
                    "ukr", "Вулиця1");
            put("pol",
                    "Street1");
        }});
        cinema2 = new Cinema();
        cinema2.setName(new HashMap<String, String>() {{
            put("eng",
                    "Cinema2");
            put(
                    "ukr", "Кінотеатр2");
            put("pol", "Cinema2");
        }});
        cinema2.setCountry(Country.UKRAINE);
        cinema2.setCity(new HashMap<String, String>() {{
            put("eng",
                    "City2");
            put(
                    "ukr", "Місто2");
            put("pol", "City2");
        }});
        cinema2.setStreet(new HashMap<String, String>() {{
            put("eng",
                    "Street2");
            put(
                    "ukr", "Вулиця2");
            put("pol",
                    "Street2");
        }});
        cinema1 = cinemaRepository.save(cinema1);
        cinema2 = cinemaRepository.save(cinema2);

        hall11 = new Hall();
        hall11.setNumber(1);
        hall11.setRows(5);
        hall11.setColumns(5);
        hall11.setTechnology(Technology._3D);
        hall11.setCinemaId(cinema1.getId());
        hall12 = new Hall();
        hall12.setNumber(2);
        hall12.setRows(10);
        hall12.setColumns(10);
        hall12.setTechnology(Technology._4D);
        hall12.setCinemaId(cinema1.getId());
        hall21 = new Hall();
        hall21.setNumber(1);
        hall21.setRows(15);
        hall21.setColumns(15);
        hall21.setTechnology(Technology._3D);
        hall21.setCinemaId(cinema2.getId());
        hall22 = new Hall();
        hall22.setNumber(1);
        hall22.setRows(20);
        hall22.setColumns(20);
        hall22.setTechnology(Technology._RM);
        hall22.setCinemaId(cinema2.getId());
        hall11 = hallRepository.save(hall11);
        hall12 = hallRepository.save(hall12);
        hall21 = hallRepository.save(hall21);
        hall22 = hallRepository.save(hall22);

        message1 = new Message();
        message1.setText(new HashMap<String, String>() {{
            put("eng",
                    "Message1");
            put(
                    "ukr", "Повідомлення1");
            put("pol", "Message1");
        }});
        message2 = new Message();
        message2.setText(new HashMap<String, String>() {{
            put("eng",
                    "Message2");
            put(
                    "ukr", "Повідомлення2");
            put("pol", "Message2");
        }});
        message1 = messageRepository.save(message1);
        message2 = messageRepository.save(message2);

        actor1 = new Actor();
        actor1.setPictureId(actorPicture1.getId());
        actor1.setFirstName(new HashMap<String, String>() {{
            put("eng",
                    "ActorFirst1");
            put(
                    "ukr", "АкторІм'я1");
            put("pol", "ActorFirst1");
        }});
        actor1.setLastName(new HashMap<String, String>() {{
            put("eng",
                    "ActorLast1");
            put(
                    "ukr", "АкторПрізвище1");
            put("pol", "ActorLast1");
        }});
        actor2 = new Actor();
        actor2.setPictureId(actorPicture2.getId());
        actor2.setFirstName(new HashMap<String, String>() {{
            put("eng",
                    "ActorFirst2");
            put(
                    "ukr", "АкторІм'я2");
            put("pol", "ActorFirst2");
        }});
        actor2.setLastName(new HashMap<String, String>() {{
            put("eng",
                    "ActorLast2");
            put(
                    "ukr", "АкторПрізвище2");
            put("pol", "ActorLast2");
        }});
        actor3 = new Actor();
        actor3.setPictureId(actorPicture3.getId());
        actor3.setFirstName(new HashMap<String, String>() {{
            put("eng",
                    "ActorFirst3");
            put(
                    "ukr", "АкторІм'я3");
            put("pol", "ActorFirst3");
        }});
        actor3.setLastName(new HashMap<String, String>() {{
            put("eng",
                    "ActorLast3");
            put(
                    "ukr", "АкторПрізвище3");
            put("pol", "ActorLast3");
        }});
        actor4 = new Actor();
        actor4.setFirstName(new HashMap<String, String>() {{
            put("eng",
                    "ActorFirst4");
            put(
                    "ukr", "АкторІм'я4");
            put("pol", "ActorFirst4");
        }});
        actor4.setLastName(new HashMap<String, String>() {{
            put("eng",
                    "ActorLast4");
            put(
                    "ukr", "АкторПрізвище4");
            put("pol", "ActorLast4");
        }});
        actor1 = actorRepository.save(actor1);
        actor2 = actorRepository.save(actor2);
        actor3 = actorRepository.save(actor3);
        actor4 = actorRepository.save(actor4);

        movie1 = new Movie();
        movie1.setFullName(new HashMap<String, String>() {{
            put("eng",
                    "Movie1 Movie1");
            put(
                    "ukr", "Фільм1 Фільм1");
            put("pol", "Movie1 Movie1");
        }});
        movie1.setName(new HashMap<String, String>() {{
            put("eng",
                    "Movie1");
            put(
                    "ukr", "Фільм1");
            put("pol", "Movie1");
        }});
        movie1.setSurname(new HashMap<String, String>() {{
            put("eng",
                    "Movie1");
            put(
                    "ukr", "Фільм1");
            put("pol", "Movie1");
        }});
        movie1.setDescription(new HashMap<String, String>() {{
            put("eng",
                    "Description1");
            put(
                    "ukr", "Опис1");
            put("pol", "Description1");
        }});
        movie1.setDirectedBy(new HashMap<String, String>() {{
            put("eng",
                    "Director1");
            put(
                    "ukr", "Режисер1");
            put("pol", "Director1");
        }});
        movie1.setDuration(150);
        movie1.setReleaseDate(LocalDate.now().plusDays(1));
        movie1.setUsersRating(8.9);
        movie1.setMpaaRating(MPAA.PG_13);
        movie1.setImdbRating((double) 9);
//        movie1.setMovieLang(Lang.ENG);
        movie1.setMovieCountry(new HashMap<String, String>() {{
            put("eng",
                    "Country1");
            put(
                    "ukr", "Країна1");
            put("pol", "Country1");
        }});
        movie1.setWidePictureId(movieWide1.getId());
        movie1.setPosterPictureId(moviePoster1.getId());
        movie1.setActorIds(List.of(actor1.getId()));
        movie1.setGenres(List.of(Genre.ACTION, Genre.ADVENTURE));

        movie2 = new Movie();
        movie2.setFullName(new HashMap<String, String>() {{
            put("eng",
                    "Movie2 Movie2");
            put(
                    "ukr", "Фільм2 Фільм2");
            put("pol", "Movie2 Movie2");
        }});
        movie2.setName(new HashMap<String, String>() {{
            put("eng",
                    "Movie2");
            put(
                    "ukr", "Фільм2");
            put("pol", "Movie2");
        }});
        movie2.setSurname(new HashMap<String, String>() {{
            put("eng",
                    "Movie2");
            put(
                    "ukr", "Фільм2");
            put("pol", "Movie2");
        }});
        movie2.setDescription(new HashMap<String, String>() {{
            put("eng",
                    "Description2");
            put(
                    "ukr", "Опис2");
            put("pol", "Description2");
        }});
        movie2.setDirectedBy(new HashMap<String, String>() {{
            put("eng",
                    "Director2");
            put(
                    "ukr", "Режисер2");
            put("pol", "Director2");
        }});
        movie2.setDuration(200);
        movie2.setReleaseDate(LocalDate.now().plusDays(2));
        movie2.setUsersRating(7.9);
        movie2.setMpaaRating(MPAA.PG_13);
        movie2.setImdbRating((double) 8);
//        movie2.setMovieLang(Lang.ENG);
        movie2.setMovieCountry(new HashMap<String, String>() {{
            put("eng",
                    "Country2");
            put(
                    "ukr", "Країна2");
            put("pol", "Country2");
        }});
        movie2.setWidePictureId(movieWide2.getId());
        movie2.setGalleryPictureIds(List.of(gallery1.getId(), gallery2.getId()));
        movie2.setActorIds(List.of(actor1.getId(), actor2.getId(), actor4.getId()));
        movie2.setGenres(List.of(Genre.ACTION, Genre.COMEDY, Genre.CRIMINAL));

        movie3 = new Movie();
        movie3.setFullName(new HashMap<String, String>() {{
            put("eng",
                    "Movie3 Movie3");
            put(
                    "ukr", "Фільм3 Фільм3");
            put("pol", "Movie3 Movie3");
        }});
        movie3.setName(new HashMap<String, String>() {{
            put("eng",
                    "Movie3");
            put(
                    "ukr", "Фільм3");
            put("pol", "Movie3");
        }});
        movie3.setSurname(new HashMap<String, String>() {{
            put("eng",
                    "Movie3");
            put(
                    "ukr", "Фільм3");
            put("pol", "Movie3");
        }});
        movie3.setDescription(new HashMap<String, String>() {{
            put("eng",
                    "Description3");
            put(
                    "ukr", "Опис3");
            put("pol", "Description3");
        }});
        movie3.setDirectedBy(new HashMap<String, String>() {{
            put("eng",
                    "Director3");
            put(
                    "ukr", "Режисер3");
            put("pol", "Director3");
        }});
        movie3.setDuration(250);
        movie3.setReleaseDate(LocalDate.now().plusDays(3));
        movie3.setUsersRating(6.9);
        movie3.setMpaaRating(MPAA.NC_17);
        movie3.setImdbRating((double) 7);
//        movie3.setMovieLang(Lang.UKR);
        movie3.setMovieCountry(new HashMap<String, String>() {{
            put("eng",
                    "Country3");
            put(
                    "ukr", "Країна3");
            put("pol", "Country3");
        }});
        movie3.setPosterPictureId(moviePoster3.getId());
        movie3.setActorIds(List.of(actor1.getId(), actor4.getId()));
        movie3.setGenres(List.of(Genre.CARTOON));

        movie4 = new Movie();
        movie4.setFullName(new HashMap<String, String>() {{
            put("eng",
                    "Movie4 Movie4");
            put(
                    "ukr", "Фільм4 Фільм4");
            put("pol", "Movie4 Movie4");
        }});
        movie4.setName(new HashMap<String, String>() {{
            put("eng",
                    "Movie4");
            put(
                    "ukr", "Фільм4");
            put("pol", "Movie4");
        }});
        movie4.setSurname(new HashMap<String, String>() {{
            put("eng",
                    "Movie4");
            put(
                    "ukr", "Фільм4");
            put("pol", "Movie4");
        }});
        movie4.setDescription(new HashMap<String, String>() {{
            put("eng",
                    "Description4");
            put(
                    "ukr", "Опис4");
            put("pol", "Description4");
        }});
        movie4.setDirectedBy(new HashMap<String, String>() {{
            put("eng",
                    "Director4");
            put(
                    "ukr", "Режисер4");
            put("pol", "Director4");
        }});
        movie4.setDuration(300);
        movie4.setReleaseDate(LocalDate.now().plusDays(4));
        movie4.setUsersRating(5.9);
        movie4.setMpaaRating(MPAA.R);
        movie4.setImdbRating((double) 6);
//        movie4.setMovieLang(Lang.POL);
        movie4.setMovieCountry(new HashMap<String, String>() {{
            put("eng",
                    "Country4");
            put(
                    "ukr", "Країна4");
            put("pol", "Country4");
        }});
        movie4.setGalleryPictureIds(List.of(gallery3.getId()));
        movie4.setActorIds(List.of(actor1.getId()));
        movie4.setGenres(List.of(Genre.ACTION, Genre.COMEDY, Genre.CRIMINAL));

        movie5 = new Movie();
        movie5.setFullName(new HashMap<String, String>() {{
            put("eng",
                    "Movie5 Movie5");
            put(
                    "ukr", "Фільм5 Фільм5");
            put("pol", "Movie5 Movie5");
        }});
        movie5.setName(new HashMap<String, String>() {{
            put("eng",
                    "Movie5");
            put(
                    "ukr", "Фільм5");
            put("pol", "Movie5");
        }});
        movie5.setSurname(new HashMap<String, String>() {{
            put("eng",
                    "Movie5");
            put(
                    "ukr", "Фільм5");
            put("pol", "Movie5");
        }});
        movie5.setDescription(new HashMap<String, String>() {{
            put("eng",
                    "Description5");
            put(
                    "ukr", "Опис5");
            put("pol", "Description5");
        }});
        movie5.setDirectedBy(new HashMap<String, String>() {{
            put("eng",
                    "Director5");
            put(
                    "ukr", "Режисер5");
            put("pol", "Director5");
        }});
        movie5.setDuration(350);
        movie5.setReleaseDate(LocalDate.now().plusDays(5));
        movie5.setUsersRating(4.9);
        movie5.setMpaaRating(MPAA.R);
        movie5.setImdbRating((double) 5);
//        movie5.setMovieLang(Lang.ENG);
        movie5.setMovieCountry(new HashMap<String, String>() {{
            put("eng",
                    "Country5");
            put(
                    "ukr", "Країна5");
            put("pol", "Country5");
        }});
        movie5.setGenres(List.of(Genre.ACTION, Genre.COMEDY, Genre.CRIMINAL,
                Genre.DOCUMENTARY, Genre.FANTASY));

        movie1 = movieRepository.save(movie1);
        movie2 = movieRepository.save(movie2);
        movie3 = movieRepository.save(movie3);
        movie4 = movieRepository.save(movie4);
        movie5 = movieRepository.save(movie5);

        s1 = new Seance();
        s1.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(8, 0, 0, 0)));
        s1.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(8, 0, 0, 0).plusMinutes(movie1.getDuration())));
        s1.setDateFrom(LocalDate.of(2021, 5, 15));
        s1.setDateTo(LocalDate.of(2021, 5, 18));
        s1.setTicketPrice((double) 15);
        s1.setMovieId(movie1.getId());
        s1.setHallId(hall11.getId());
        s1.setDays(List.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SUNDAY));

        s2 = new Seance();
        s2.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(16, 15, 0, 0)));
        s2.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(16, 15, 0, 0).plusMinutes(movie1.getDuration())));
        s2.setDateFrom(LocalDate.of(2021, 5, 19));
        s2.setDateTo(LocalDate.of(2021, 5, 27));
        s2.setTicketPrice((double) 20);
        s2.setMovieId(movie1.getId());
        s2.setHallId(hall11.getId());
        s2.setDays(List.of(Day.TUESDAY, Day.THURSDAY, Day.SATURDAY));

        s3 = new Seance();
        s3.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(8, 0, 0, 0)));
        s3.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(8, 0, 0, 0).plusMinutes(movie1.getDuration())));
        s3.setDateFrom(LocalDate.of(2021, 5, 16));
        s3.setDateTo(LocalDate.of(2021, 5, 23));
        s3.setTicketPrice((double) 15);
        s3.setMovieId(movie1.getId());
        s3.setHallId(hall12.getId());
        s3.setDays(List.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SUNDAY));

        s4 = new Seance();
        s4.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(18, 0, 0, 0)));
        s4.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(18, 0, 0, 0).plusMinutes(movie1.getDuration())));
        s4.setDateFrom(LocalDate.of(2021, 5, 24));
        s4.setDateTo(LocalDate.of(2021, 5, 26));
        s4.setTicketPrice((double) 20);
        s4.setMovieId(movie1.getId());
        s4.setHallId(hall12.getId());
        s4.setDays(List.of(Day.TUESDAY, Day.THURSDAY, Day.SATURDAY));

        s5 = new Seance();
        s5.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(8, 0, 0, 0)));
        s5.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(8, 0, 0, 0).plusMinutes(movie2.getDuration())));
        s5.setDateFrom(LocalDate.of(2021, 5, 24));
        s5.setDateTo(LocalDate.of(2021, 5, 25));
        s5.setTicketPrice((double) 15);
        s5.setMovieId(movie2.getId());
        s5.setHallId(hall11.getId());
        s5.setDays(List.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SUNDAY));

        s6 = new Seance();
        s6.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(9, 0, 0, 0)));
        s6.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(9, 0, 0, 0).plusMinutes(movie3.getDuration())));
        s6.setDateFrom(LocalDate.of(2021, 5, 15));
        s6.setDateTo(LocalDate.of(2021, 5, 19));
        s6.setTicketPrice((double) 20);
        s6.setMovieId(movie3.getId());
        s6.setHallId(hall21.getId());
        s6.setDays(List.of(Day.TUESDAY, Day.THURSDAY, Day.SATURDAY));

        s7 = new Seance();
        s7.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(14, 20, 0, 0)));
        s7.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(14, 20, 0, 0).plusMinutes(movie5.getDuration())));
        s7.setDateFrom(LocalDate.of(2021, 5, 20));
        s7.setDateTo(LocalDate.of(2021, 5, 23));
        s7.setTicketPrice((double) 15);
        s7.setMovieId(movie5.getId());
        s7.setHallId(hall21.getId());
        s7.setDays(List.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SUNDAY));

        s8 = new Seance();
        s8.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(23, 0, 0, 0)));
        s8.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(23, 0, 0, 0).plusMinutes(movie5.getDuration())));
        s8.setDateFrom(LocalDate.of(2021, 5, 17));
        s8.setDateTo(LocalDate.of(2021, 5, 27));
        s8.setTicketPrice((double) 20);
        s8.setMovieId(movie5.getId());
        s8.setHallId(hall11.getId());
        s8.setDays(List.of(Day.TUESDAY, Day.THURSDAY, Day.SATURDAY));

        s9 = new Seance();
        s9.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(14, 20, 0, 0)));
        s9.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(14, 20, 0, 0).plusMinutes(movie2.getDuration())));
        s9.setDateFrom(LocalDate.of(2021, 5, 18));
        s9.setDateTo(LocalDate.of(2021, 5, 21));
        s9.setTicketPrice((double) 15);
        s9.setMovieId(movie2.getId());
        s9.setHallId(hall22.getId());
        s9.setDays(List.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SUNDAY));

        s10 = new Seance();
        s10.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(20, 0, 0, 0)));
        s10.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.of(20, 0, 0, 0).plusMinutes(movie5.getDuration())));
        s10.setDateFrom(LocalDate.of(2021, 5, 22));
        s10.setDateTo(LocalDate.of(2021, 5, 26));
        s10.setTicketPrice((double) 20);
        s10.setMovieId(movie5.getId());
        s10.setHallId(hall21.getId());
        s10.setDays(List.of(Day.TUESDAY, Day.THURSDAY, Day.SATURDAY));

        s1 = seanceRepository.save(s1);
        s2 = seanceRepository.save(s2);
        s3 = seanceRepository.save(s3);
        s4 = seanceRepository.save(s4);
        s5 = seanceRepository.save(s5);
        s6 = seanceRepository.save(s6);
        s7 = seanceRepository.save(s7);
        s8 = seanceRepository.save(s8);
        s9 = seanceRepository.save(s9);
        s10 = seanceRepository.save(s10);

        ticket1 = new Ticket();
        ticket1.setDate(LocalDate.of(2021, 5, 25));
        ticket1.setPlaceRow(4);
        ticket1.setPlaceSeat(3);
        ticket1.setSeanceId(s2.getId());
        ticket2 = new Ticket();
        ticket2.setDate(LocalDate.of(2021, 5, 25));
        ticket2.setPlaceRow(5);
        ticket2.setPlaceSeat(5);
        ticket2.setSeanceId(s2.getId());
        ticket3 = new Ticket();
        ticket3.setDate(LocalDate.of(2021, 5, 19));
        ticket3.setPlaceRow(20);
        ticket3.setPlaceSeat(3);
        ticket3.setPlaceAllowance((double) 15);
        ticket3.setSeanceId(s9.getId());
        ticket1 = ticketRepository.save(ticket1);
        ticket2 = ticketRepository.save(ticket2);
        ticket3 = ticketRepository.save(ticket3);

        user = new User();
        user.setFirstName("User");
        user.setLastName("User");
        user.setUsername("User");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        user.setPhone("+380506693793");
        user.setHomeCity("City1");
        user.setHomeCountry(Country.UKRAINE);
        user.setAboutMe("User");
        user.setSex(Sex.MALE);
        user.setTicketIds(List.of(ticket2.getId(), ticket3.getId()));
        user.setPictureId(userPicture1.getId());
        user.setSelectedLang(Lang.UKR);
        user.setSelectedCinemaId(cinema1.getId());
        user.setFavouriteMovieIds(List.of(movie1.getId(), movie3.getId()));
        user.setViewedMovieIds(List.of(movie1.getId(), movie2.getId()));
        user.setWaitMovieIds(List.of(movie3.getId()));

        worker = new User();
        worker.setFirstName("Worker");
        worker.setLastName("Worker");
        worker.setUsername("Worker");
        worker.setPassword("worker");
        worker.setRole(Role.WORKER);
        worker.setEmail("worker@gmail.com");
        worker.setPhone("+380506693794");
        worker.setHomeCity("City2");
        worker.setHomeCountry(Country.UKRAINE);
        worker.setAboutMe("Worker");

        admin = new User();
        admin.setPictureId(userPicture2.getId());
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setUsername("Admin");
        admin.setPassword("admin");
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@gmail.com");
        admin.setPhone("+380506693795");
        admin.setHomeCity("City3");
        admin.setHomeCountry(Country.UNITED_KINGDOM);

        user = userRepository.save(user);
        worker = userRepository.save(worker);
        admin = userRepository.save(admin);

        rating1 = new Rating();
        rating1.setMovieId(movie3.getId());
        rating1.setUserId(user.getId());
        rating1.setUserRating(4);
        rating2 = new Rating();
        rating2.setMovieId(movie1.getId());
        rating2.setUserId(user.getId());
        rating2.setUserRating(5);
        rating3 = new Rating();
        rating3.setMovieId(movie3.getId());
        rating3.setUserId(worker.getId());
        rating3.setUserRating(3);
        rating1 = ratingRepository.save(rating1);
        rating2 = ratingRepository.save(rating2);
        rating3 = ratingRepository.save(rating3);

        comment1 = new Comment();
        comment1.setDate(LocalDate.now());
        comment1.setTime(LocalDateTime.now().withYear(1).withMonth(1).withDayOfMonth(1));
        comment1.setText("Comment1");
        comment1.setMovieId(movie5.getId());
        comment1.setUserId(user.getId());
        comment1.setReported(true);
        comment2 = new Comment();
        comment2.setDate(LocalDate.now().minusDays(1));
        comment2.setTime(LocalDateTime.now().withYear(1).withMonth(1).withDayOfMonth(1).minusHours(1));
        comment2.setText("Comment2");
        comment2.setMovieId(movie3.getId());
        comment2.setUserId(user.getId());
        comment2.setRatingId(rating1.getId());
        comment3 = new Comment();
        comment3.setDate(LocalDate.now().minusDays(2));
        comment3.setTime(LocalDateTime.now().withYear(1).withMonth(1).withDayOfMonth(1).minusHours(2));
        comment3.setText("Comment3");
        comment3.setMovieId(movie1.getId());
        comment3.setUserId(admin.getId());
        comment1 = commentRepository.save(comment1);
        comment2 = commentRepository.save(comment2);
        comment3 = commentRepository.save(comment3);
    }


}
