package com.userRed.redesigned.exception;

public class UserAlreadyRegisteredException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UserAlreadyRegisteredException(String smth) {
        super("User " + smth + " already exists.");
    }


}
