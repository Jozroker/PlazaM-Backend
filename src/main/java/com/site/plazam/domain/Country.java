package com.site.plazam.domain;

public enum Country {

    UNITED_KINGDOM("/resources/img/png/en.png"),
    UKRAINE("/resources/img/png/ua.png"),
    POLAND("/resources/img/png/pl.png");

    private final String flagPicture;

    Country(String flagPicture) {
        this.flagPicture = flagPicture;
    }

    public String getFlagPicture() {
        return flagPicture;
    }
}
