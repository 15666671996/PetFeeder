package com.group2.pet_feeder.controller;

import com.group2.pet_feeder.service.Auth_Service_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class Auth_Controller {
    @Autowired
    private Auth_Service_Interface service;

    @RequestMapping("/login")
    public void login(String username,String password){


    }



}
