package com.cloud.authservice.controller;

import com.cloud.authservice.entity.Action;
import com.cloud.authservice.entity.Token;
import com.cloud.authservice.exception.NoUserFoundException;
import com.cloud.authservice.request.LoginRequest;
import com.cloud.authservice.entity.User;
import com.cloud.authservice.security.CustomUserDetailsService;
import com.cloud.authservice.service.ActionService;
import com.cloud.authservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.cloud.authservice.security.SecurityConstants.SECRET;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ActionService actionService;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @ApiOperation("Login to the system with user credentials")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        SecurityContextHolder.clearContext();
        User user = null;
        try {
            user = userService.getByUsername(loginRequest.getUsername());
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Token token = userService.generateToken(user);
            actionService.add(Action.newAction(user, Action.ActionType.LOGIN, Action.ActionStatus.SUCCESSFUL, null));
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            if (user != null) {
                actionService.add(Action.newAction(user, Action.ActionType.LOGIN, Action.ActionStatus.FAILED, "Wrong password entered."));
                return new ResponseEntity<>("Wrong password entered.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong username entered.", HttpStatus.OK);
            }
        }
    }

    @ApiOperation("Logout from the system with user credentials")
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("You successfully logged out", HttpStatus.OK);
    }
}
