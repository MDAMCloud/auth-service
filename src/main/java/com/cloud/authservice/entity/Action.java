package com.cloud.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Entity
public class Action extends BaseEntity{

    @Id
    @Field("_id")
    private String id;

    private String userId;

    private String username;

    private String actionType;

    @Column(nullable = false)
    private String actionStatus;

    private String failReason;

    public Action() {
    }

    public static Action newAction(User user, ActionType actionType, ActionStatus status, String failReason){
        Action action = new Action();
        action.setUserId(user.getId());
        action.setUsername(user.getUsername());
        action.setActionType(actionType);
        action.setActionStatus(status);
        action.setFailReason(failReason);
        return action;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActionType getActionType() {
        return ActionType.valueOf(this.actionType);
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType.name();
    }

    public ActionStatus getActionStatus() {
        return ActionStatus.valueOf(this.actionStatus);
    }

    public void setActionStatus(ActionStatus status) {
        this.actionStatus = status.name();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public enum ActionStatus {
        SUCCESSFUL,
        FAILED
    }

    public enum ActionType {
        LOGIN,
        ADD_USER,
        SEND_MESSAGE
    }
}
