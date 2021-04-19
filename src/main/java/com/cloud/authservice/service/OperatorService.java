package com.cloud.authservice.service;

import com.cloud.authservice.entity.Action;
import com.cloud.authservice.entity.User;
import com.cloud.authservice.exception.NoUserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {

    @Autowired
    private UserService userService;

    @Autowired
    private ActionService actionService;

    public User addUser(User newUser) {
        User user = null;
        try {
            user = userService.getByUsername(newUser.getUsername());
            actionService.add(Action.newAction(user, Action.ActionType.ADD_USER, Action.ActionStatus.FAILED, "A user with this username already exists."));
            return null;
        } catch (NoUserFoundException e) {
            user = userService.add(newUser);
            actionService.add(Action.newAction(user, Action.ActionType.ADD_USER, Action.ActionStatus.SUCCESSFUL, null));
            return user;
        }
    }

    public void deleteUser(User user) {
        userService.delete(user);
    }
}
