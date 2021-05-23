package com.site.plazam.service.impl;

import com.site.plazam.dto.ActorCreateDTO;
import com.site.plazam.dto.ActorForActorListDTO;
import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.repository.ActorRepository;
import com.site.plazam.service.ActorService;
import com.site.plazam.service.mapper.ActorMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository ar;

    private final ActorMapper am;

    public ActorServiceImpl(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.ar = actorRepository;
        this.am = actorMapper;
    }

    @Override
    public ActorForActorListDTO save(ActorCreateDTO actorCreateDTO) {
        return am.toActorForActorListDTO(ar.save(am.toEntity(actorCreateDTO)));
    }

    @Override
    public ActorForActorListDTO findById(String id) {
        return ar.findById(id).map(am::toActorForActorListDTO).orElse(null);
    }

    @Override
    public List<ActorForActorListDTO> findByFirstNameOrLastName(String firstName, String lastName) {
        List<ActorForActorListDTO> actorList;
        actorList =
                ar.findByFirstNameOrLastName(firstName, lastName)
                        .stream()
                        .map(am::toActorForActorListDTO)
                        .collect(Collectors.toList());
        return actorList;
    }

    @Override
    public List<ActorForActorListDTO> findAll() {
        List<ActorForActorListDTO> actorList;
        actorList = ar.findAll()
                .stream()
                .map(am::toActorForActorListDTO)
                .collect(Collectors.toList());
        return actorList;
    }

    @Override
    public void delete(ActorSimpleDTO actor) {
        ar.deleteById(actor.getId());
    }

    @Override
    public void deleteAll() {
        ar.deleteAll();
    }
}
