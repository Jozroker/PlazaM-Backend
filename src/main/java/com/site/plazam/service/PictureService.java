package com.site.plazam.service;

import com.site.plazam.dto.parents.PictureDTO;

import java.util.List;

public interface PictureService {

    PictureDTO save(PictureDTO pictureDTO, Class className);

    PictureDTO findById(String id, Class className);

    List<PictureDTO> findAll(Class className);

    void delete(PictureDTO picture, Class className);

    void deleteAll(Class className);
}
