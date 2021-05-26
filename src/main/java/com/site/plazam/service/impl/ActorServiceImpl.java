package com.site.plazam.service.impl;

import com.site.plazam.domain.ActorPicture;
import com.site.plazam.domain.Movie;
import com.site.plazam.dto.ActorCreateDTO;
import com.site.plazam.dto.ActorForActorListDTO;
import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.repository.ActorRepository;
import com.site.plazam.service.ActorService;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.PictureService;
import com.site.plazam.service.mapper.ActorMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository ar;

    private final ActorMapper am;

    private final MongoTemplate mt;

    @Lazy
    private final PictureService ps;

    @Lazy
    private final MovieService ms;

    public ActorServiceImpl(ActorRepository actorRepository,
                            ActorMapper actorMapper,
                            @Lazy PictureService pictureService,
                            MongoTemplate mongoTemplate,
                            @Lazy MovieService movieService) {
        this.ar = actorRepository;
        this.am = actorMapper;
        this.ps = pictureService;
        this.mt = mongoTemplate;
        this.ms = movieService;
    }

    @Override
    @Transactional
    public ActorForActorListDTO save(ActorCreateDTO actorCreateDTO) {
        if (actorCreateDTO.getPicture() != null) {
            if (actorCreateDTO.getPicture().getId() == null) {
                ActorForActorListDTO actor = findById(actorCreateDTO.getId());
                if (actor != null) {
                    ps.delete(actor.getPicture(), ActorPicture.class);
                }
                actorCreateDTO.setPicture(ps.save(actorCreateDTO.getPicture(),
                        ActorPicture.class));
            }
        }
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
    @Transactional
    public void delete(ActorSimpleDTO actor) {
        PictureDTO picture = findById(actor.getId()).getPicture();
        if (picture.getId() != null) {
            ps.delete(picture, ActorPicture.class);
        }
        ms.removeActorFromMovies(actor);
        ar.deleteById(actor.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        ps.deleteAll(ActorPicture.class);
        Query query = new Query();
        Update update = new Update().set("actorIds", new ArrayList<>());
        mt.findAndModify(query, update, Movie.class);
        ar.deleteAll();
    }
}
