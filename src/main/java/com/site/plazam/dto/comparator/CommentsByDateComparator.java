package com.site.plazam.dto.comparator;

import com.site.plazam.dto.CommentForCommentsListDTO;

import java.time.LocalDate;
import java.util.Comparator;

public class CommentsByDateComparator implements Comparator<CommentForCommentsListDTO> {

    @Override
    public int compare(CommentForCommentsListDTO comment1,
                       CommentForCommentsListDTO comment2) {
        LocalDate date1 = comment1.getDate();
        LocalDate date2 = comment2.getDate();
        if (date1.getYear() == date2.getYear()) {
            if (date1.getMonthValue() == date2.getMonthValue()) {
                return date2.getDayOfMonth() - date1.getDayOfMonth();
            }
            return date2.getMonthValue() - date1.getMonthValue();
        }
        return date2.getYear() - date1.getYear();
    }
}
