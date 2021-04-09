package com.cloud.authservice.repository;

import com.cloud.authservice.entity.Action;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionRepository extends MongoRepository<Action, String> {
    List<Action> findAllByUserId(String userId);

    List<Action> findAllByUserIdAndActionType(String userId, String actionType);
}
