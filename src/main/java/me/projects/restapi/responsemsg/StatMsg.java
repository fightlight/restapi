package me.projects.restapi.responsemsg;

import me.projects.restapi.model.User;
import me.projects.restapi.model.UserStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatMsg {
    private Date requestTimestamp;
    private List<PartUser> users;

    public StatMsg(Date requestTimestamp, List<User> users) {
        this.requestTimestamp = requestTimestamp;

        this.users = new ArrayList<>();
        for (User u : users) {
            this.users.add(new PartUser(u.getUsername(), u.getImageUri(), u.getUserStatus()));
        }
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public List<PartUser> getUsers() {
        return users;
    }

    class PartUser {
        private String username;
        private String imageUri;
        private UserStatus userStatus;

        public PartUser(String username, String imageUri, UserStatus userStatus) {
            this.username = username;
            this.imageUri = imageUri;
            this.userStatus = userStatus;
        }

        public String getUsername() {
            return username;
        }

        public String getImageUri() {
            return imageUri;
        }

        public UserStatus getUserStatus() {
            return userStatus;
        }
    }
}
