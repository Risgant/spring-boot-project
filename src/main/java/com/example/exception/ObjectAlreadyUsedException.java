package com.example.exception;

public class ObjectAlreadyUsedException extends RuntimeException{
    public ObjectAlreadyUsedException() {
    }

    public ObjectAlreadyUsedException(String message) {
        super(message);
    }
}
