package com.site.plazam.domain;

public enum Technology {

    _2D("2D"),
    _3D("3D"),
    _4D("4D"),
    _RM("RM"),
    _RM_PLUS("RM+");

    private final String type;

    Technology(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
