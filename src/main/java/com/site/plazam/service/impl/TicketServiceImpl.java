package com.site.plazam.service.impl;

import com.site.plazam.dto.SeanceForTicketDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.repository.TicketRepository;
import com.site.plazam.service.TicketService;
import com.site.plazam.service.mapper.TicketMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository tr;

    private final TicketMapper tm;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketMapper ticketMapper) {
        this.tr = ticketRepository;
        this.tm = ticketMapper;
    }

    @Override
    public TicketSimpleDTO save(TicketSimpleDTO ticketSimpleDTO) {
        return tm.toDTO(tr.save(tm.toEntity(ticketSimpleDTO)));
    }

    @Override
    public TicketSimpleDTO findById(String id) {
        return tr.findById(id).map(tm::toDTO).orElse(null);
        //todo change null callback to errors throwing
    }

    @Override
    public List<TicketSimpleDTO> findAll() {
        return tr.findAll().stream().map(tm::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketSimpleDTO> findBySeance(SeanceForTicketDTO seance) {
        return tr.findBySeanceId(seance.getId()).stream().map(tm::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketSimpleDTO> findByDate(LocalDate date) {
        return tr.findByDate(date).stream().map(tm::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(TicketSimpleDTO ticket) {
        tr.deleteById(ticket.getId());
    }

    @Override
    public void deleteAll() {
        tr.deleteAll();
    }
}
