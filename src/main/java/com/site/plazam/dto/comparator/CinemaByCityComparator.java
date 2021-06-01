package com.site.plazam.dto.comparator;

import com.site.plazam.dto.parents.CinemaDTO;

import java.util.Comparator;

public class CinemaByCityComparator implements Comparator<CinemaDTO> {

    @Override
    public int compare(CinemaDTO cinema1,
                       CinemaDTO cinema2) {
        if (cinema1.getCity().equalsIgnoreCase(cinema2.getCity())) {
            return cinema1.getName().compareToIgnoreCase(cinema2.getName());
        }
        return cinema1.getCity().compareToIgnoreCase(cinema2.getCity());
    }
}
