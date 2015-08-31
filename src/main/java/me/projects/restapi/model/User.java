package me.projects.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
public class User {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    @NotNull
    @Pattern(regexp="[a-zA-Z0-9]+",
            message="Username can contain only digits and latin letters")
    private String username;
    @Column
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Email format is incorrect")
    private String email;
    @Column
    @Pattern(regexp = "/([a-zA-Z0-9]+/)+[0-9]+_[0-9]+\\.(?i:jpg)",
            message = "URI format is incorrect")
    private String imageUri;
    @Column
    private UserStatus userStatus;
    @Column
    private Date statusUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

}
