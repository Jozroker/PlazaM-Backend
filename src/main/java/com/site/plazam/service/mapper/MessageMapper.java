package com.site.plazam.service.mapper;

import com.site.plazam.domain.Lang;
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
        Lang locale;
        try {
            locale =
                    Lang.valueOf(LocaleContextHolder.getLocale().getISO3Language().toUpperCase());
        } catch (Exception e) {
            locale =
                    Lang.ENG;
        }
        return map.get(locale.name().toLowerCase());
    }
}
