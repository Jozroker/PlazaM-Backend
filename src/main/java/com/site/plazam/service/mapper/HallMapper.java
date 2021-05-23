package com.site.plazam.service.mapper;

import com.site.plazam.domain.Hall;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.HallForTicketDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface HallMapper {

    HallForTicketDTO toHallForTicketDTO(Hall hall);

    HallForSeanceDTO toHallForSeanceDTO(Hall hall);
}
