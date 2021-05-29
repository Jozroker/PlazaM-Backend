package com.site.plazam.repository;

import com.site.plazam.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findByDate(LocalDate date);

    List<Ticket> findBySeanceId(String id);

    void deleteByDateBefore(LocalDate date);
}
