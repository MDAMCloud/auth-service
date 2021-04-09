package com.cloud.authservice.controller;

import com.cloud.authservice.entity.Errors;
import com.cloud.authservice.entity.User;
import com.cloud.authservice.exception.NoUserFoundException;
import com.cloud.authservice.request.UserRequest;
import com.cloud.authservice.request.UserUpdateRequest;
import com.cloud.authservice.security.CustomUserDetailsService;
import com.cloud.authservice.service.ErrorsService;
import com.cloud.authservice.service.OperatorService;
import com.cloud.authservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    ErrorsService errorsService;

    @ApiOperation("Add a new user")
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserRequest request) {
        User addedUser = operatorService.addUser(request.toUser());
        if (addedUser == null) {
            return new ResponseEntity<>("A user with given username already exits.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(addedUser, HttpStatus.OK);
        }
    }

    @ApiOperation("Get current user")
    @GetMapping
    public ResponseEntity<?> getCurrent() {
        User user = null;
        try {
            user = customUserDetailsService.getCurrentUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            errorsService.add(Errors.newError(null, null, "You are not logged in."));
            return new ResponseEntity<>("You are not logged in.", HttpStatus.OK);
        }

    }

    @ApiOperation("Get all users")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation("Get user by given id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            User user = userService.get(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoUserFoundException e) {
            errorsService.add(Errors.newError(id, null, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @ApiOperation("Update user by given id")
    @PutMapping
    public ResponseEntity<?> updateUserById(@RequestBody UserUpdateRequest request) {
        try {
            User updatedUser = userService.update(request.toUser());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (NoUserFoundException e) {
            errorsService.add(Errors.newError(request.getId(), null, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @ApiOperation("Delete user by given id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        try {
            User userToDelete = userService.get(id);
            operatorService.deleteUser(userToDelete);
            return new ResponseEntity<>("User deleted.", HttpStatus.OK);
        } catch (NoUserFoundException e) {
            errorsService.add(Errors.newError(id, null, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
