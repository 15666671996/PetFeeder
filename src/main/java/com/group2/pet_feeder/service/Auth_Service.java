package com.group2.pet_feeder.service;

import com.group2.pet_feeder.entity.User;
import com.group2.pet_feeder.repository.Auth_Repository_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class Auth_Service implements Auth_Service_Interface{

    @Autowired
    private Auth_Repository_Interface repository;

    @Override
    public boolean login(User user) {
        if("tester".equalsIgnoreCase(user.getUsername())&&"123456".equalsIgnoreCase(user.getPassword())){
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("user",user.getUsername());
            return true;
        }else {
            return false;
        }


    }

    @Override
    public void ttt() {
        repository.ttt();
    }
}
