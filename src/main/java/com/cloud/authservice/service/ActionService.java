package com.cloud.authservice.service;

import com.cloud.authservice.entity.Action;
import com.cloud.authservice.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActionService {
    @Autowired
    private ActionRepository actionRepository;

    public Action add(Action action) {
        return actionRepository.save(action);
    }

    public List<Action> getAll() {
        List<Action> actions = (List<Action>) actionRepository.findAll();
        return actions;
    }

    public List<Action> getAllByUserId(String userId) {
        List<Action> actions = actionRepository.findAllByUserId(userId);
        return actions;
    }

    public List<Action> getAllByUserIdAndActionType(String userId, Action.ActionType actionType) {
        List<Action> actions = actionRepository.findAllByUserIdAndActionType(userId, actionType.name());
        return actions;
    }
}
