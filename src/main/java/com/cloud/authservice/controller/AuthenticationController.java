package com.cloud.authservice.controller;

import com.cloud.authservice.entity.Action;
import com.cloud.authservice.entity.Token;
import com.cloud.authservice.request.LoginRequest;
import com.cloud.authservice.entity.User;
import com.cloud.authservice.security.CustomUserDetailsService;
import com.cloud.authservice.service.ActionService;
import com.cloud.authservice.service.UserService;
import com.cloud.authservice.utils.AppResponse;
import com.cloud.authservice.utils.AppResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AppResponse<Token> login(@RequestBody LoginRequest loginRequest) {
        SecurityContextHolder.clearContext();
        User user = null;
        try {
            user = userService.getByUsername(loginRequest.getUsername());
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Token token = userService.generateToken(user);
            actionService.add(Action.newAction(user, Action.ActionType.LOGIN, Action.ActionStatus.SUCCESSFUL, null));
            return AppResponses.from(token);
        } catch (Exception e) {
            if (user != null) {
                actionService.add(Action.newAction(user, Action.ActionType.LOGIN, Action.ActionStatus.FAILED, "Wrong password entered."));
                return AppResponses.failure("Wrong password entered.");
            } else {
                return AppResponses.failure("Wrong username entered.");
            }
        }
    }

    // Bu işlem fe tarafında gerçekleşmeli token client içerisinde olduğundan dolayı
    @ApiOperation("Logout from the system with user credentials")
    @GetMapping("/logout")
    public AppResponse logout() {
        SecurityContextHolder.clearContext();
        return AppResponses.successful();
    }
}
