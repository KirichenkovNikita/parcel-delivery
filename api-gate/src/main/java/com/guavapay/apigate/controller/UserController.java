package com.guavapay.apigate.controller;

import com.guavapay.apigate.exception.EmailAlreadyExistsException;
import com.guavapay.apigate.exception.UserAlreadyExistsException;
import com.guavapay.apigate.model.User;
import com.guavapay.apigate.service.user.UserService;
import com.guavapay.datalib.user.UserCreateRequest;
import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("Controller to create a user")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    public static final String CREATE_MESSAGE = "User successfully created";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("User creation. Checking for uniqueness of email and username")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody UserCreateRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok(CREATE_MESSAGE);
        } catch (EmailAlreadyExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserAlreadyExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
