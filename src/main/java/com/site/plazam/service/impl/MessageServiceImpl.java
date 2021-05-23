package com.site.plazam.service.impl;

import com.site.plazam.dto.MessageCreateDTO;
import com.site.plazam.dto.MessageForUserDTO;
import com.site.plazam.dto.parents.MessageSimpleDTO;
import com.site.plazam.repository.MessageRepository;
import com.site.plazam.service.MessageService;
import com.site.plazam.service.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository mr;

    private final MessageMapper mm;

    public MessageServiceImpl(MessageRepository messageRepository,
                              MessageMapper messageMapper) {
        this.mr = messageRepository;
        this.mm = messageMapper;
    }

    @Override
    public MessageForUserDTO save(MessageCreateDTO messageCreateDTO) {
        return mm.toDTO(mm.toEntity(messageCreateDTO));
    }

    @Override
    public MessageForUserDTO findById(String id) {
        return mr.findById(id).map(mm::toDTO).orElse(null);
    }

    @Override
    public List<MessageForUserDTO> findAll() {
        return mr.findAll().stream().map(mm::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(MessageSimpleDTO message) {
        mr.deleteById(message.getId());
    }

    @Override
    public void deleteAll() {
        mr.deleteAll();
    }
}
