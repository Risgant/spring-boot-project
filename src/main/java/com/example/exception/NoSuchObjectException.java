package com.example.exception;

public class NoSuchObjectException extends RuntimeException{
    public NoSuchObjectException() {
        super("Such object does not exist");

    }

    public NoSuchObjectException(String str){
        super(str);
    }
}
