package com.group2.pet_feeder.service;

import com.group2.pet_feeder.repository.Auth_Repository_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Auth_Service implements Auth_Service_Interface{

    @Autowired
    private Auth_Repository_Interface repository;


    @Override
    public void login() {

    }
}
