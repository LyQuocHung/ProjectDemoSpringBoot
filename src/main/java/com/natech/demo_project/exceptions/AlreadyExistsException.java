package com.natech.demo_project.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String meassage) {
        super(meassage);
    }
}
