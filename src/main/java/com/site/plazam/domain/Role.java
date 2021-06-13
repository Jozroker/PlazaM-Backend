package com.site.plazam.domain;

import java.io.Serializable;

public enum Role implements Serializable {
    USER, ADMIN, WORKER;

    public String getRole() {
        return this.name();
    }
}
