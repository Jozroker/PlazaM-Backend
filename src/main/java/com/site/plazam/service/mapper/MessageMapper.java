package com.site.plazam.service.mapper;

import com.site.plazam.domain.Message;
import com.site.plazam.dto.MessageCreateDTO;
import com.site.plazam.dto.MessageForUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "text", target = "text", qualifiedByName =
            "toString")
    MessageForUserDTO toDTO(Message message);

    Message toEntity(MessageCreateDTO messageCreateDTO);

    default String toString(Map<String, String> map) {
        return map.get(LocaleContextHolder.getLocale().getISO3Language());
    }
}
