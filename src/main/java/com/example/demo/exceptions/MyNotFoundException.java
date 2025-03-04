package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MyNotFoundException extends ResponseStatusException {
    public MyNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST ,message);
    }
}
