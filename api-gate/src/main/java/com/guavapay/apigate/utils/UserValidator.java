package com.guavapay.apigate.utils;

import com.guavapay.apigate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    private static final String NULL_USER_MESSAGE = "When creating, the user cannot be null";
    private static final String USER_NOT_FOUNT = "User not found id = %d";

    private final UserRepository repository;

    @Autowired
    public UserValidator(UserRepository repository) {
        this.repository = repository;
    }

    public void checkUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(NULL_USER_MESSAGE);
        }

        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException(String.format(USER_NOT_FOUNT, id));
        }
    }
}
