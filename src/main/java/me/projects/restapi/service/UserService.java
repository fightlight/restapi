package me.projects.restapi.service;

import me.projects.restapi.dto.UserStatusDto;
import me.projects.restapi.exceptions.ServiceException;
import me.projects.restapi.model.User;
import me.projects.restapi.model.UserStatus;
import me.projects.restapi.responsemsg.UserStatusMsg;

import java.util.Date;
import java.util.List;

public interface UserService {
    User add(User user) throws ServiceException;
    User get(Long id) throws ServiceException;
    UserStatusMsg updateStatus(UserStatusDto userStatusDto) throws ServiceException;
    List<User> getAll();
    List<User> getAll(UserStatus userStatus, Date statusUpdateTime);
    List<User> getAll(UserStatus userStatus);
    List<User> getAll(Date statusUpdateTime);
}
