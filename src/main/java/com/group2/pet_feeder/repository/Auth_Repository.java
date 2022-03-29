package com.group2.pet_feeder.repository;

import com.group2.pet_feeder.entity.User;
import com.group2.pet_feeder.other.ID_Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Auth_Repository implements Auth_Repository_Interface {

    @Autowired
    private JdbcTemplate template;

    @Override
    public HashMap<String, Object> login(User user) {
        HashMap<String, Object> rtn = new HashMap<>();
        try {
            String sql = "select * from user where username=? and password=?";
            List<Map<String, Object>> result = template.queryForList(sql, user.getUsername(), user.getPassword());
            if (result.size() > 0) {
                rtn.put("message", "success");
            } else {
                rtn.put("message", "Incorrect username or password");
            }
        } catch (DataAccessException e) {
            rtn.put("message", "Error");
            System.out.println(e.getMessage());
        }
        return rtn;
    }

    @Override
    public HashMap<String, Object> register(User user) {
        HashMap<String, Object> rtn = new HashMap<>();
        try {
            String sql = "insert into user(userID,username,password) values (?,?,?)";
            template.update(sql, ID_Generator.getUUID(), user.getUsername(), user.getPassword());
            rtn.put("message", "success");
        } catch (DataAccessException e) {
            if (e.getCause().getMessage().contains("Duplicate") && e.getCause().getMessage().contains("PRIMARY")) {
                return register(user);
            } else if (e.getCause().getMessage().contains("Duplicate") && e.getCause().getMessage().contains("username")) {
                rtn.put("message", "username exist!");
                System.out.println(e.getMessage());
            } else {
                rtn.put("message", "Error");
                System.out.println(e.getMessage());
            }
        }
        return rtn;
    }

    @Override
    public HashMap<String, Object> checkUsername(String username) {
        HashMap<String, Object> rtn = new HashMap<>();
        try {
            String sql = "select COUNT(*) from user where username='" + username + "'";
            int result = template.queryForObject(sql, Integer.class);
            if (result == 0) {
                rtn.put("message", "available");
            } else {
                rtn.put("message", "unavailable");
            }
        } catch (DataAccessException e) {
            rtn.put("message", "Error");
            System.out.println(e.getMessage());
        }
        return rtn;
    }
}
