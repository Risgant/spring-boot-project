package com.example.exception;

public class NoSuchEntityException extends RuntimeException{
    public NoSuchEntityException() {
        super();
    }

    public NoSuchEntityException(String str){
        super(str);
    }
}
