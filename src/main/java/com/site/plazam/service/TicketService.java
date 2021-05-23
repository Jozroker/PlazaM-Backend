package com.site.plazam.service;

import com.site.plazam.dto.SeanceForTicketDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {

    TicketSimpleDTO save(TicketSimpleDTO ticketSimpleDTO);

    TicketSimpleDTO findById(String id);

    List<TicketSimpleDTO> findAll();

    List<TicketSimpleDTO> findBySeance(SeanceForTicketDTO seance);

    List<TicketSimpleDTO> findByDate(LocalDate date);

    void delete(TicketSimpleDTO ticket);

    void deleteAll();
}
