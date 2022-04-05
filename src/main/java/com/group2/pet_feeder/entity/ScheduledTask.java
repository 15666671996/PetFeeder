package com.group2.pet_feeder.entity;

import java.time.LocalTime;

public class ScheduledTask {

    private String userId;
    private LocalTime time;

    public ScheduledTask(String userId, LocalTime time) {
        this.userId = userId;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "(" + userId + ", " + time+")";
    }
}
