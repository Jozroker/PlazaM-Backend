package com.site.plazam.config;

import com.site.plazam.service.SeanceService;
import com.site.plazam.service.TicketService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@EnableScheduling
public class Scheduler {

    private final SeanceService seanceService;

    private final TicketService ticketService;

    public Scheduler(SeanceService seanceService, TicketService ticketService) {
        this.seanceService = seanceService;
        this.ticketService = ticketService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanSeances() {
        seanceService.deleteByDateToBefore(LocalDate.now());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanTickets() {
        LocalDate date = LocalDate.now();
        LocalDateTime time = LocalDateTime.of(LocalDate.of(1, 1, 1),
                LocalTime.now());
        ticketService.deleteByDateBeforeAndTimeBefore(date, time);
    }
}
