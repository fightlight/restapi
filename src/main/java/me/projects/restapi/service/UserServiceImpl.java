package me.projects.restapi.service;

import me.projects.restapi.dao.UserRepository;
import me.projects.restapi.dto.UserStatusDto;
import me.projects.restapi.exceptions.ServiceException;
import me.projects.restapi.model.User;
import me.projects.restapi.model.UserStatus;
import me.projects.restapi.responsemsg.UserStatusMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User add(User user) throws ServiceException {
        // check username for uniqueness
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ServiceException("Can't add user. User already exists");
        }
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ServiceException("Can't add user", e);
        }
    }

    @Override
    public User get(Long id) throws ServiceException {
        User user;
        try {
            user = userRepository.findOne(id);
        } catch (Exception e) {
            throw new ServiceException("Can't get user", e);
        }
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return user;
    }

    @Override
    @Transactional
    public UserStatusMsg updateStatus(UserStatusDto userStatusDto) throws ServiceException {
        User proxyUser;
        try {
            proxyUser = userRepository.getOne(userStatusDto.getId());
        } catch (Exception e) {
            throw new ServiceException("Can't update user status", e);
        }


        if (proxyUser == null) {
            throw new ServiceException("User not found");
        }

        // save old status
        UserStatus oldUserStatus = proxyUser.getUserStatus();
        try {
            // update status if changed
            if (oldUserStatus == null || !oldUserStatus.equals(userStatusDto.getUserStatus())) {
                proxyUser.setUserStatus(userStatusDto.getUserStatus());
                proxyUser.setStatusUpdateTime(new Date());
                userRepository.save(proxyUser);
            }
        } catch (Exception e) {
            throw new ServiceException("Can't update user status", e);
        }

        // imitation of remote DB/API
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new ServiceException("Remote DB is unavailable", e);
        }
        return new UserStatusMsg(proxyUser.getId(), oldUserStatus, proxyUser.getUserStatus());
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAll(UserStatus userStatus, Date statusUpdateTime) {
        return userRepository.findByUserStatusAndStatusUpdateTimeGreaterThan(userStatus, statusUpdateTime);
    }

    @Override
    public List<User> getAll(UserStatus userStatus) {
        return userRepository.findByUserStatus(userStatus);
    }

    @Override
    public List<User> getAll(Date statusUpdateTime) {
        return userRepository.findByStatusUpdateTimeGreaterThan(statusUpdateTime);
    }
}
