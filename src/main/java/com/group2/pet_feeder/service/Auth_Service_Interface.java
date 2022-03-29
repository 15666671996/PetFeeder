package com.group2.pet_feeder.service;

import com.group2.pet_feeder.entity.User;

import java.util.HashMap;

public interface Auth_Service_Interface {

    HashMap<String, Object> login(User user);

    byte[] getPhoto();
}
