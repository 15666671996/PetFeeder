package com.group2.pet_feeder.repository;

import com.group2.pet_feeder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Auth_Repository implements Auth_Repository_Interface{

    @Autowired
    private JdbcTemplate template;

    @Override
    public HashMap<String, Object> login(User user) {
        HashMap<String, Object> rtn = new HashMap<>();
        try {
            String sql = "select * from user where username=? and password=?";
            List<Map<String, Object>> result = template.queryForList(sql, user.getUsername(), user.getPassword());
            System.out.println(result.get(0));
            if (result.size() > 0) {
                rtn.put("message", "success");
            } else {
                rtn.put("message", "Incorrect username or password");
            }
        } catch (DataAccessException e) {
            rtn.put("message", "Connection exception");
            System.out.println(e.getMessage());
        }
        return rtn;
    }
}
