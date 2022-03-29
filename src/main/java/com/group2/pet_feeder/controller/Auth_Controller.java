package com.group2.pet_feeder.controller;

import com.group2.pet_feeder.entity.User;
import com.group2.pet_feeder.service.Auth_Service_Interface;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HashMap<String, Object> login(String username, String password){
        System.out.println(username+"==="+password);
        return service.login(new User(username,password));

    }

    @RequestMapping("/test")
    public HashMap<String,Object> test(String a, String b, HttpServletRequest request, HttpServletResponse response){
        System.out.println(a+b);

        HashMap<String,Object> map  = new HashMap<>();
        map.put("code",response.getStatus());
        map.put("message","success");
        map.put("data",null);


        return map;
    }
    @RequestMapping("/getPhoto")
    public byte[] test(){
        System.out.println("getPhoto");
        return service.getPhoto();
    }


}
