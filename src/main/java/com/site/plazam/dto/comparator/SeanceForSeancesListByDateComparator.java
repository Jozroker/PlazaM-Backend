package com.site.plazam.dto.comparator;

import com.site.plazam.dto.SeanceForSeancesListDTO;

import java.time.LocalDate;
import java.util.Comparator;

public class SeanceForSeancesListByDateComparator implements Comparator<SeanceForSeancesListDTO> {

    @Override
    public int compare(SeanceForSeancesListDTO seance1,
                       SeanceForSeancesListDTO seance2) {
        LocalDate date1 = seance1.getDateFrom();
        LocalDate date2 = seance2.getDateFrom();
        if (date1.getYear() == date2.getYear()) {
            if (date1.getMonthValue() == date2.getMonthValue()) {
                return date1.getDayOfMonth() - date2.getDayOfMonth();
            }
            return date1.getMonthValue() - date2.getMonthValue();
        }
        return date1.getYear() - date2.getYear();
    }
}
