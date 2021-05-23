package com.site.plazam.service;

import com.site.plazam.dto.MessageCreateDTO;
import com.site.plazam.dto.MessageForUserDTO;
import com.site.plazam.dto.parents.MessageSimpleDTO;

import java.util.List;

public interface MessageService {

    MessageForUserDTO save(MessageCreateDTO messageCreateDTO);

    MessageForUserDTO findById(String id);

    List<MessageForUserDTO> findAll();

    void delete(MessageSimpleDTO message);

    void deleteAll();
}
