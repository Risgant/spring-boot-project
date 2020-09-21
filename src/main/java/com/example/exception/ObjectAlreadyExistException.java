package com.example.exception;

public class ObjectAlreadyExistException extends RuntimeException{
    public ObjectAlreadyExistException() {
        super("Such object already exist");
    }

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
