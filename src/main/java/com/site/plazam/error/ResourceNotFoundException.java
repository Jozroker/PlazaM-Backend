package com.site.plazam.error;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceType, String id) {
        super(resourceType + " with id " + id + " not found");
    }

    public ResourceNotFoundException(Class classs, String id) {
        super(classs.getSimpleName() + "object with id " + id + " not found");
    }
}
