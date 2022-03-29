package com.group2.pet_feeder.controller;

import com.group2.pet_feeder.entity.User;
import com.group2.pet_feeder.service.Auth_Service_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
public class Auth_Controller {
    @Autowired
    private Auth_Service_Interface service;

    @RequestMapping("/login")
    public HashMap<String, Object> login(String username, String password) {
        return service.login(new User(username, password));
    }

    @RequestMapping("/register")
    public HashMap<String, Object> register(String username, String password) {
        if (username.length() > 0 && password.length() > 0) {
            return service.register(new User(username, password));
        } else {
            HashMap<String, Object> rtn = new HashMap<>();
            rtn.put("message", "illegal format!");
            return rtn;
        }
    }

    @RequestMapping("/checkUsername")
    public HashMap<String, Object> checkUsername(String username) {
        return service.checkUsername(username);
    }

    @RequestMapping(value = "/photo.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto() {
        return service.getPhoto();
    }


}
