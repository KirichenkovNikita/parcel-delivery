package com.guavapay.apigate.controller;

import com.guavapay.apigate.exception.UserAlreadyExistsException;
import com.guavapay.apigate.service.user.UserService;
import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("Controller to user details")
@RestController
@RequestMapping(value = "/api/userdetails")
public class UserDetailsController {
    public static final String CREATE_DETAIL_MESSAGE = "User details successfully created";
    private final UserService userService;

    @Autowired
    public UserDetailsController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Adding additional user information")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createDetails(@RequestBody UserDetailServiceCreateRequest request) {
        try {
            userService.createDetails(request);
            return ResponseEntity.ok(CREATE_DETAIL_MESSAGE);
        } catch (UserAlreadyExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get additional information about a user")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST)
    public ResponseEntity createDetails(@PathVariable(value="id") Long id) {
        try {
            return ResponseEntity.ok(userService.getDetails(id));
        } catch (UserAlreadyExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
