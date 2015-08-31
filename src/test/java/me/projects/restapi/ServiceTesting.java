package me.projects.restapi;

import junit.framework.Assert;
import me.projects.restapi.exceptions.ServiceException;
import me.projects.restapi.model.User;
import me.projects.restapi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApi.class)

public class ServiceTesting {
    @Autowired
    private UserService userv;

    @Test(expected = ServiceException.class)
    public void addNullUserTest() throws ServiceException {
        userv.add(null);
    }

    @Test
    public void addUserTest() throws ServiceException {
        User user = new User();
        user.setUsername("testname" + user.hashCode());
        userv.add(user);
        Assert.assertEquals(true, user.getId() != null);

    }

    @Test
    public void getUserTest() throws ServiceException {
        User user = new User();
        String username = "testname" + user.hashCode();
        user.setUsername(username);
        userv.add(user);
        Assert.assertEquals(true, userv.get(user.getId()).getUsername().equals(username));
    }
}