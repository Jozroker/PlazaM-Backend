package com.site.plazam.service;

import com.site.plazam.dto.ActorCreateDTO;
import com.site.plazam.dto.ActorForActorListDTO;
import com.site.plazam.dto.parents.ActorSimpleDTO;

import java.util.List;

public interface ActorService {

    ActorForActorListDTO save(ActorCreateDTO actorCreateDTO);

    ActorForActorListDTO findById(String id);

    List<ActorForActorListDTO> findAll();

    List<ActorForActorListDTO> findByFirstNameOrLastName(String firstName,
                                                         String lastName);

    void delete(ActorSimpleDTO actor);

    void deleteAll();
}
