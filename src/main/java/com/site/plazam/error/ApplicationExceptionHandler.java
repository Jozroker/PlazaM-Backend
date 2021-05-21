package com.site.plazam.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResource(ResourceNotFoundException exception) {
//        log.error("resource not found", exception);
        return "not_found";
    }
}
