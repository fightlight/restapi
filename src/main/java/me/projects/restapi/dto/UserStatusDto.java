package me.projects.restapi.dto;

import me.projects.restapi.model.UserStatus;

import javax.validation.constraints.NotNull;

public class UserStatusDto {
    @NotNull
    private Long id;
    @NotNull
    private UserStatus userStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}