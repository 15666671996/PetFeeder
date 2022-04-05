package com.group2.pet_feeder.controller;

import com.group2.pet_feeder.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
public class Controller {

    @Autowired
    private Service service;

    @RequestMapping(value = "/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto() {
        return service.getPhoto();
    }

    @RequestMapping("/getTasks")
    public HashMap<String,Object> getTasks(HttpSession session){
        return service.getTasks((String) session.getAttribute("userId"));
    }

}
