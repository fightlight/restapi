package me.projects.restapi.controller;

import me.projects.restapi.dto.UserStatusDto;
import me.projects.restapi.exceptions.ControllerException;
import me.projects.restapi.exceptions.ServiceException;
import me.projects.restapi.model.User;
import me.projects.restapi.model.UserStatus;
import me.projects.restapi.responsemsg.ImageUriMsg;
import me.projects.restapi.responsemsg.StatMsg;
import me.projects.restapi.responsemsg.UserStatusMsg;
import me.projects.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class RestApiController {

    @Value("${uploadDir}")
    private String uploadDir;
    @Autowired
    private UserService userService;

    // 1 - upload image
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ImageUriMsg uploadImage(HttpServletRequest req,
                                   @RequestParam("file") MultipartFile file) throws IOException, ServletException, ControllerException {
        if (file.isEmpty()) {
            throw new ControllerException("File is empty.");
        }
        if (!file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
            throw new ControllerException("File type is not jpeg");
        }
        // gets absolute path of the web application
        String appPath = req.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + uploadDir;
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        // create unique file name
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'));
        String fileName = (new Date().getTime()) + "_" + file.hashCode() + fileExt;
        // create file path
        String filePath = savePath + File.separator + fileName;
        // write file to server
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytes = file.getBytes();
            bos.write(bytes);
            return new ImageUriMsg("/" + uploadDir + "/" + fileName);
        } catch (Exception e) {
            throw new ControllerException("Failed to upload file", e);
        }
    }


    // 2 - add user: save username, email, imageUri
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Long addUser(@RequestBody @Valid final User user) throws ServiceException {
        return userService.add(user).getId();
    }


    // 3 - get user info
    @RequestMapping(value = "/users/info/{userId}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable Long userId) throws ServiceException {
        return userService.get(userId);
    }


    // 4 - change user status
    @RequestMapping(value = "/users/status", method = RequestMethod.PUT)
    public UserStatusMsg updateUserStatus(@RequestBody UserStatusDto userStatusDto) throws ServiceException {
        return userService.updateStatus(userStatusDto);
    }


    // 5 - statistics
    @RequestMapping(value = "/stat/userStatus/{userStatus}/timestamp/{timestamp}", method = RequestMethod.GET)
    public StatMsg stat(@PathVariable UserStatus userStatus, @PathVariable Long timestamp) {
        List<User> users = userService.getAll(userStatus, new Date(timestamp));
        return new StatMsg(new Date(), users);
    }

    @RequestMapping(value = "/stat/userStatus/{userStatus}", method = RequestMethod.GET)
    public StatMsg stat(@PathVariable UserStatus userStatus) {
        List<User> users = userService.getAll(userStatus);
        return new StatMsg(new Date(), users);
    }

    @RequestMapping(value = "/stat/timestamp/{timestamp}", method = RequestMethod.GET)
    public StatMsg stat(@PathVariable Long timestamp) {
        List<User> users = userService.getAll(new Date(timestamp));
        return new StatMsg(new Date(), users);
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public StatMsg stat() throws InterruptedException {
        List<User> users = userService.getAll();
        return new StatMsg(new Date(), users);
    }
}
