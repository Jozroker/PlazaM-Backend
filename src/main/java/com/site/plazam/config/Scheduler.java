//package com.site.plazam.config;
//
//import com.cinema.point.dto.SeanceCreationDTO;
//import com.cinema.point.service.SeanceService;
//import com.cinema.point.service.TicketService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@Configuration
//@EnableScheduling
//@Slf4j
//public class Scheduler {
//
//    private final SeanceService seanceService;
//
//    private final TicketService ticketService;
//
//    public Scheduler(SeanceService seanceService, TicketService ticketService) {
//        this.seanceService = seanceService;
//        this.ticketService = ticketService;
//    }
//
//    @Scheduled(cron = "0 0 0 * * *")
//    public void cleanSeances() {
//        log.info("cleaning seances");
//        Date tomorrow = Date.valueOf(LocalDate.now().minus(1, ChronoUnit.DAYS));
//        List<SeanceCreationDTO> deleting = seanceService.findBySeanceDateTo(tomorrow);
//        for (SeanceCreationDTO seanceCreationDTO : deleting) {
//            seanceService.deleteById(seanceCreationDTO.getId());
//        }
//    }
//
//    @Scheduled(cron = "0 0 * * * *")
//    public void cleanTickets() {
//        log.info("cleaning tickets");
//        Date date = Date.valueOf(LocalDate.now());
//        Time time = Time.valueOf(LocalTime.now());
//        ticketService.deleteByDate(date, time);
//    }
//}
