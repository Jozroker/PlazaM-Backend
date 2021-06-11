package com.site.plazam.dto.comparator;

import com.site.plazam.dto.CommentForCommentsListDTO;

import java.util.Comparator;

public class CommentsByMovieNameComparator implements Comparator<CommentForCommentsListDTO> {

    @Override
    public int compare(CommentForCommentsListDTO comment1,
                       CommentForCommentsListDTO comment2) {
        if (comment1.getMovie().getName().equals(comment2.getMovie().getName())) {
            return comment1.getMovie().getSurname().compareTo(comment2.getMovie().getSurname());
        }
        return comment1.getMovie().getName().compareTo(comment2.getMovie().getName());
    }
}
