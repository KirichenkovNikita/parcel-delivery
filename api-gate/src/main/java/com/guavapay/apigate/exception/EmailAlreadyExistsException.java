package com.guavapay.apigate.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public static final String MESSAGE = "A user with the same email already exists";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
