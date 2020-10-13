package com.example.exception;

public class WrongPasswordOrLoginException extends RuntimeException{
    public WrongPasswordOrLoginException() {
        super("Wrong username or password");

    }

    public WrongPasswordOrLoginException(String str){
        super(str);
    }
}
