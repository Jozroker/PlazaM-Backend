//package com.site.plazam.service;
//
//import com.site.plazam.domain.Day;
//import com.site.plazam.dto.HallForSeanceDTO;
//import com.site.plazam.dto.MovieForSeanceDTO;
//import com.site.plazam.dto.SeanceCreateDTO;
//import com.site.plazam.dto.SeanceForSeancesListDTO;
//import com.site.plazam.error.TimeAlreadyScheduledException;
//import org.junit.Assert;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class SeanceServiceTest {
//
//    @Autowired
//    SeanceService seanceService;
//
//    @Autowired
//    MovieService movieService;
//
//    @Autowired
//    HallService hallService;
//
//    @Autowired
//    CinemaService cinemaService;
//
//    static String hall4Id = "60b14ff40bf64624cec1dc62";
//    static String movie4Id = "60b14ff40bf64624cec1dc6c";
//    static SeanceForSeancesListDTO seanceForSeancesListDTO;
//    static SeanceCreateDTO seanceCreateDTO;
//    List<SeanceForSeancesListDTO> seanceForSeancesListDTOList;
//    Page<Map.Entry> seances;
//
//    @Test
//    @Order(1)
//    void saveErr1() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 0)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(12, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SUNDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            System.out.println("Success");
//            System.out.println(seanceForSeancesListDTO.toString());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//    }
//
//    @Test
//    @Order(2)
//    void saveErr2() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 30)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(12, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SUNDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            System.out.println("Success");
//            System.out.println(seanceForSeancesListDTO.toString());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//    }
//
//    @Test
//    @Order(3)
//    void saveErr3() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(7, 30)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 30)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SUNDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            System.out.println("Success");
//            System.out.println(seanceForSeancesListDTO.toString());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//    }
//
//    @Test
//    @Order(4)
//    void saveErr4() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 30)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 50)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SUNDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            System.out.println("Success");
//            System.out.println(seanceForSeancesListDTO.toString());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//    }
//
//    @Test
//    @Order(5)
//    void saveErr5() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(7, 30)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(12, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SUNDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            System.out.println("Success");
//            System.out.println(seanceForSeancesListDTO.toString());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//    }
//
//
//    @Test
//    @Order(6)
//    void saveTrueTest() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc62"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 0)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(10, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SATURDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            System.out.println("Success");
//            System.out.println(seanceForSeancesListDTO.toString());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//    }
//
//    @Test
//    @Order(7)
//    void deleteTrueTest() {
//        seanceService.delete(seanceForSeancesListDTO);
//    }
//
//    @Test
//    @Order(8)
//    void save() {
//        try {
//            seanceCreateDTO = new SeanceCreateDTO();
//            seanceCreateDTO.setMovie(movieService.findMovieForResultListById(movie4Id));
//            seanceCreateDTO.setHall(hallService.findHallForTicketById(hall4Id));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(3, 0)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(5, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,22));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 26));
//            seanceCreateDTO.setTicketPrice(15.5);
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.MONDAY, Day.TUESDAY, Day.FRIDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//            seanceCreateDTO.setId(seanceForSeancesListDTO.getId());
//            System.out.println(seanceForSeancesListDTO.toString());
//            System.out.println("===========================");
//            Assert.assertNotNull(seanceForSeancesListDTO.getId());
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("ERROR");
//        }
//    }
//
//    @Test
//    @Order(9)
//    void updateError() {
//        try {
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(7, 30)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(12, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 5,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 5, 17));
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SUNDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//        System.out.println(seanceForSeancesListDTO.toString());
//        System.out.println("===========================");
//    }
//
//    @Test
//    @Order(10)
//    void updateTrue() {
//        try {
//            seanceCreateDTO.setHall(hallService.findHallForTicketById("60b14ff40bf64624cec1dc5f"));
//            seanceCreateDTO.setStartSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(8, 0)));
//            seanceCreateDTO.setEndSeance(LocalDateTime.of(LocalDate.of(1, 1,
//                    1), LocalTime.of(10, 0)));
//            seanceCreateDTO.setDateFrom(LocalDate.of(2021, 4,15));
//            seanceCreateDTO.setDateTo(LocalDate.of(2021, 4, 17));
//            seanceCreateDTO.setDays(com.sun.tools.javac.util.List.of(Day.SATURDAY));
//            seanceForSeancesListDTO = seanceService.save(seanceCreateDTO);
//        } catch (TimeAlreadyScheduledException e) {
//            System.out.println("Error");
//        }
//        System.out.println(seanceForSeancesListDTO.toString());
//        System.out.println("===========================");
//    }
//
//    @Test
//    @Order(11)
//    void findById() {
//        seanceForSeancesListDTO =
//                seanceService.findSeanceForSeancesListById(seanceForSeancesListDTO.getId());
//        System.out.println(seanceForSeancesListDTO);
//        System.out.println("===========================");
//        Assert.assertNotNull(seanceForSeancesListDTO);
//    }
//
//    @Test
//    @Order(12)
//    void findAll() {
//        seanceForSeancesListDTOList =
//                seanceService.findSeanceForSeancesListAll();
//        System.out.println(seanceForSeancesListDTOList.size());
//        System.out.println("===========================");
//        Assert.assertFalse(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(13)
//    void findByHall() {
//        seanceForSeancesListDTOList =
//                seanceService.findByHall(hallService.findHallForSeanceById(hall4Id));
//        System.out.println("[");
//        seanceForSeancesListDTOList.forEach(seance -> System.out.println(seance.toString()));
//        System.out.println("]");
//        System.out.println("===========================");
//        Assert.assertFalse(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(14)
//    void findByMovie() {
//        seanceForSeancesListDTOList =
//                seanceService.findByMovie(movieService.findMovieForSeanceById("60b14ff40bf64624cec1dc69"));
//        System.out.println("[");
//        seanceForSeancesListDTOList.forEach(seance -> System.out.println(seance.toString()));
//        System.out.println("]");
//        System.out.println("===========================");
//        Assert.assertFalse(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(15)
//    void findByDateFromBeforeAndDateToAfter() {
//        seanceForSeancesListDTOList =
//                seanceService.findByDateFromBeforeAndDateToAfter(LocalDate.of(2021, 5, 25), LocalDate.of(2021,5,25));
//        System.out.println("[");
//        seanceForSeancesListDTOList.forEach(seance -> System.out.println(seance.toString()));
//        System.out.println("]");
//        System.out.println("===========================");
//        Assert.assertFalse(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(16)
//    void findByDateFromBetweenOrDateToBetween() {
//        seanceForSeancesListDTOList =
//                seanceService.findByDateFromBetweenOrDateToBetween(LocalDate.of(2021, 5, 25),
//                        LocalDate.of(2021,5,27));
//        System.out.println("[");
//        seanceForSeancesListDTOList.forEach(seance -> System.out.println(seance.toString()));
//        System.out.println("]");
//        System.out.println("===========================");
//        Assert.assertFalse(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(17)
//    void findByStartSeanceBeforeAndEndSeanceAfter() {
//        seanceForSeancesListDTOList =
//                seanceService.findByStartSeanceBeforeAndEndSeanceAfter(LocalDateTime.now().withHour(8).withMinute(0),
//                        LocalDateTime.now().withHour(8).withMinute(15));
//        System.out.println("[");
//        seanceForSeancesListDTOList.forEach(seance -> System.out.println(seance.toString()));
//        System.out.println("]");
//        System.out.println("===========================");
//        Assert.assertTrue(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(18)
//    void findByStartSeanceBetweenAndEndSeanceBetween() {
//        seanceForSeancesListDTOList =
//                seanceService.findByStartSeanceBetweenOrEndSeanceBetween(LocalDateTime.now().withHour(16).withMinute(0),
//                        LocalDateTime.now().withHour(19).withMinute(0));
//        System.out.println("[");
//        seanceForSeancesListDTOList.forEach(seance -> System.out.println(seance.toString()));
//        System.out.println("]");
//        System.out.println("===========================");
//        Assert.assertFalse(seanceForSeancesListDTOList.isEmpty());
//    }
//
//    @Test
//    @Order(19)
//    void findSeancesList() {
//        seances = seanceService.findSeancesList(LocalDate.of(2021, 5, 24),
//                cinemaService.findById("60b14ff40bf64624cec1dc5d"),
//                PageRequest.of(0 ,1));
//        System.out.println(seances.getTotalPages());
//        System.out.println("[");
//        seances.forEach(entry -> {
//            MovieForSeanceDTO movie = (MovieForSeanceDTO) entry.getKey();
//            Map<LocalDate, Map<HallForSeanceDTO, List<SeanceForSeancesListDTO>>> schedule =
//                    (Map<LocalDate, Map<HallForSeanceDTO,
//                            List<SeanceForSeancesListDTO>>>) entry.getValue();
//            System.out.println(" " + movie.toString());
//            System.out.println(" [");
//            schedule.keySet().forEach(date -> {
//                System.out.println("  " + date.toString());
//                System.out.println("  [");
//                schedule.get(date).keySet().forEach(hall -> {
//                    System.out.println("   " + hall.toString());
//                    System.out.println("   [");
//                    for (SeanceForSeancesListDTO seancesListDTO :
//                            schedule.get(date).get(hall)) {
//                        System.out.println("    " + seancesListDTO.toString());
//                    }
//                    System.out.println("   ]");
//                });
//                System.out.println("  ]");
//            });
//            System.out.println(" ]");
//
//        });
//        System.out.println("]");
//    }
//
//    @Test
//    @Order(20)
//    void deleteByDateToBefore() {
//        seanceService.deleteByDateToBefore(LocalDate.of(2021, 5, 1));
//        seanceForSeancesListDTO =
//                seanceService.findSeanceForSeancesListById(seanceForSeancesListDTO.getId());
//        Assert.assertNull(seanceForSeancesListDTO);
//    }
//
//    @Test
//    @Order(21)
//    void delete() {
//        save();
//        Assert.assertNotNull(seanceForSeancesListDTO);
//        seanceService.delete(seanceForSeancesListDTO);
//        seanceForSeancesListDTO =
//                seanceService.findSeanceForSeancesListById(seanceForSeancesListDTO.getId());
//        Assert.assertNull(seanceForSeancesListDTO);
//    }
//}
