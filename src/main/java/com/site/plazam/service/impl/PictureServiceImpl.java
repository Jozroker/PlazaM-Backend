package com.site.plazam.service.impl;

import com.site.plazam.domain.ActorPicture;
import com.site.plazam.domain.MoviePicture;
import com.site.plazam.domain.UserPicture;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.repository.ActorPictureRepository;
import com.site.plazam.repository.MoviePictureRepository;
import com.site.plazam.repository.UserPictureRepository;
import com.site.plazam.service.PictureService;
import com.site.plazam.service.mapper.PictureMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private final ActorPictureRepository apr;

    private final MoviePictureRepository mpr;

    private final UserPictureRepository upr;

    private final PictureMapper pm;

    public PictureServiceImpl(ActorPictureRepository actorPictureRepository,
                              MoviePictureRepository moviePictureRepository,
                              UserPictureRepository userPictureRepository,
                              PictureMapper pictureMapper) {
        this.apr = actorPictureRepository;
        this.mpr = moviePictureRepository;
        this.upr = userPictureRepository;
        this.pm = pictureMapper;
    }

    @Override
    public PictureDTO save(PictureDTO pictureDTO, Class className) {
        if (className == ActorPicture.class) {
            pictureDTO = pm.toDTO(apr.save(pm.toActorPictureEntity(pictureDTO)));
        } else if (className == MoviePicture.class) {
            pictureDTO = pm.toDTO(mpr.save(pm.toMoviePictureEntity(pictureDTO)));
        } else if (className == UserPicture.class) {
            pictureDTO = pm.toDTO(upr.save(pm.toUserPictureEntity(pictureDTO)));
        }
        return pictureDTO;
    }

    @Override
    public PictureDTO findById(String id, Class className) {
        PictureDTO pictureDTO = null;
        if (className == ActorPicture.class) {
            pictureDTO = apr.findById(id).map(pm::toDTO).orElse(null);
        } else if (className == MoviePicture.class) {
            pictureDTO = mpr.findById(id).map(pm::toDTO).orElse(null);
        } else if (className == UserPicture.class) {
            pictureDTO = upr.findById(id).map(pm::toDTO).orElse(null);
        }
        return pictureDTO;
    }

    @Override
    public List<PictureDTO> findAll(Class className) {
        List<PictureDTO> pictureDTOList = new ArrayList<>();
        if (className == ActorPicture.class) {
            pictureDTOList = apr.findAll().stream().map(pm::toDTO).collect(Collectors.toList());
        } else if (className == MoviePicture.class) {
            pictureDTOList = mpr.findAll().stream().map(pm::toDTO).collect(Collectors.toList());
        } else if (className == UserPicture.class) {
            pictureDTOList = upr.findAll().stream().map(pm::toDTO).collect(Collectors.toList());
        }
        return pictureDTOList;
    }

    @Override
    public void delete(PictureDTO picture, Class className) {
        if (picture != null) {
            if (picture.getId() != null) {
                if (className == ActorPicture.class) {
                    apr.deleteById(picture.getId());
                } else if (className == MoviePicture.class) {
                    mpr.deleteById(picture.getId());
                } else if (className == UserPicture.class) {
                    upr.deleteById(picture.getId());
                }
            }
        }
    }

    @Override
    public void deleteAll(Class className) {
        if (className == ActorPicture.class) {
            apr.deleteAll();
        } else if (className == MoviePicture.class) {
            mpr.deleteAll();
        } else if (className == UserPicture.class) {
            upr.deleteAll();
        }
    }
}
