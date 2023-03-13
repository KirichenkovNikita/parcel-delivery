package com.guavapay.apigate.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public static final String MESSAGE = "A user with the same name already exists";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
