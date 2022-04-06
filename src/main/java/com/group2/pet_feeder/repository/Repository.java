package com.group2.pet_feeder.repository;

import com.group2.pet_feeder.other.ID_Generator;
import com.group2.pet_feeder.other.ScheduleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class Repository {
    @Autowired
    private JdbcTemplate template;

    public List<Map<String, Object>> selectAllTasks() {
        List<Map<String, Object>> rtn = null;
        try {
            String sql = "select * from task";
            rtn = template.queryForList(sql);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        return rtn;
    }

    public HashMap<String, Object> selectTaskById(String userId) {
        HashMap<String, Object> rtn = new HashMap<>();
        try {
            String sql = "select * from task where userId=?";
            List<Map<String, Object>> result = template.queryForList(sql, userId);
            if (result.size() > 0) {
                rtn.put("message", "success");
                rtn.put("data", result);
            } else {
                rtn.put("message", "Nothing find!");
            }
        } catch (DataAccessException e) {
            rtn.put("message", "Error");
            System.out.println(e.getMessage());
        }
        return rtn;
    }

    public HashMap<String, Object> insertTask(String userId, String time) {
        HashMap<String, Object> rtn = new HashMap<>();
        try {
            String sql = "insert into task(userId,time) values (?,?)";
            template.update(sql, userId, time);
            rtn.put("message", "success");
        }catch (DataAccessException e) {
            rtn.put("message", "Error");
            System.out.println(e.getMessage());
        }
        return rtn;
    }
}
