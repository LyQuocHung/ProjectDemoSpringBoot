package com.natech.demo_project.exceptions;

public class ResourNotFoundException extends RuntimeException {
    public ResourNotFoundException(String message) {
        super(message);
    }
}
