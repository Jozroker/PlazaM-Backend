package com.site.plazam.error;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceType, String id) {
        super(resourceType + " with id " + id + " not found");
    }

    public ResourceNotFoundException(Class className, String value) {
        super(className.getSimpleName() + "object with id/name " + value + " " +
                "not" +
                " found");
    }
}
