package com.site.plazam.domain;

public enum Country {

    UNITED_KINGDOM("/resources/img/png/en.png", Lang.ENG),
    UKRAINE("/resources/img/png/ua.png", Lang.UKR),
    POLAND("/resources/img/png/pl.png", Lang.POL);

    private final String flagPicture;

    private final Lang language;

    Country(String flagPicture, Lang lang) {
        this.flagPicture = flagPicture;
        this.language = lang;
    }

    public String getFlagPicture() {
        return this.flagPicture;
    }

    public Lang getLanguage() {
        return this.language;
    }
}
