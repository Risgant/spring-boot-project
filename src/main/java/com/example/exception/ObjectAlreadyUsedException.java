package com.example.exception;

public class ObjectAlreadyUsedException extends RuntimeException{
    public ObjectAlreadyUsedException() {
        super("Such object already used");
    }

    public ObjectAlreadyUsedException(String message) {
        super(message);
    }
}
