package com.site.plazam.service.mapper;

import com.site.plazam.domain.Cinema;
import com.site.plazam.dto.parents.CinemaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CinemaMapper {

    @Mapping(source = "name", target = "name", qualifiedByName =
            "toString")
    @Mapping(source = "city", target = "city", qualifiedByName =
            "toString")
    @Mapping(source = "street", target = "street", qualifiedByName =
            "toString")
    CinemaDTO toDTO(Cinema cinema);

    default String toString(Map<String, String> map) {
        return map.get(LocaleContextHolder.getLocale().getISO3Language());
    }
}
