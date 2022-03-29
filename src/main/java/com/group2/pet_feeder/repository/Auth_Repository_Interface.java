package com.group2.pet_feeder.repository;

import com.group2.pet_feeder.entity.User;

import java.util.HashMap;

public interface Auth_Repository_Interface {
    HashMap<String, Object> login(User user);

    HashMap<String, Object> register(User user);

    HashMap<String, Object> checkUsername(String username);
}
