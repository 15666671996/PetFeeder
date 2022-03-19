package com.group2.pet_feeder.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class Auth_Repository implements Auth_Repository_Interface{

    @Autowired
    private JdbcTemplate template;

    @Override
    public void ttt() {
//        jdbcTemplate.update("insert into txl (bh,xm,dh) values (?,?,?)",new Object[]{txl.getBh(),txl.getXm(),txl.getDh()});
        List<Map<String, Object>> maps = template.queryForList("select * from user");

        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
}
