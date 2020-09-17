package com.example.exception;

public class SuchObjectAlreadyExist extends RuntimeException{
    public SuchObjectAlreadyExist() {
    }

    public SuchObjectAlreadyExist(String message) {
        super(message);
    }
}
