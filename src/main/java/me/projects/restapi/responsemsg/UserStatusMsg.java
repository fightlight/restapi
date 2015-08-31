package me.projects.restapi.responsemsg;

import me.projects.restapi.model.UserStatus;

public class UserStatusMsg {
    private Long userId;
    private UserStatus oldUserStatus;
    private UserStatus newUserStatus;

    public UserStatusMsg(Long userId, UserStatus oldUserStatus, UserStatus newUserStatus) {
        this.userId = userId;
        this.oldUserStatus = oldUserStatus;
        this.newUserStatus = newUserStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserStatus getOldUserStatus() {
        return oldUserStatus;
    }

    public void setOldUserStatus(UserStatus oldUserStatus) {
        this.oldUserStatus = oldUserStatus;
    }

    public UserStatus getNewUserStatus() {
        return newUserStatus;
    }

    public void setNewUserStatus(UserStatus newUserStatus) {
        this.newUserStatus = newUserStatus;
    }
}
