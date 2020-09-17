package com.example.exception;

public class NoSuchObjectException extends RuntimeException{
    public NoSuchObjectException() {
        super();
    }

    public NoSuchObjectException(String str){
        super(str);
    }
}
