package com.site.plazam.domain;

public enum MPAA {

    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String rating;

    MPAA(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }
}
