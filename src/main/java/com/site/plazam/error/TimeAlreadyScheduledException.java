package com.site.plazam.error;

import com.site.plazam.dto.SeanceCreateDTO;

public class TimeAlreadyScheduledException extends RuntimeException {

    public TimeAlreadyScheduledException(SeanceCreateDTO seance) {
        super("On this time seance is already exist." + "<br> Current seance: " + seance.toString());
    }
}
