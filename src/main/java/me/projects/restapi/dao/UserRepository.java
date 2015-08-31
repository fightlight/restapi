package me.projects.restapi.dao;

import me.projects.restapi.model.UserStatus;
import me.projects.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
    List<User> findByUserStatus(UserStatus userStatus);
    List<User> findByStatusUpdateTimeGreaterThan(Date statusUpdateTime);
    List<User> findByUserStatusAndStatusUpdateTimeGreaterThan(UserStatus userStatus, Date statusUpdateTime);
}
