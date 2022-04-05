package com.group2.pet_feeder.other;

import com.group2.pet_feeder.entity.ScheduledTask;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ScheduleManager {

    private static ConcurrentLinkedQueue<ScheduledTask> queue;

    private static Timer timer = new Timer();

    public static void init(List<Map<String, Object>> tasks) {
        LinkedList<ScheduledTask> list = new LinkedList<>();
        for (Map<String, Object> task : tasks) {
            String[] time = ((String) task.get("time")).split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            list.add(new ScheduledTask((String) task.get("userId"), LocalTime.of(hour, minute)));
        }
        LocalTime currentTime = LocalTime.now();
        list.sort(new Comparator<ScheduledTask>() {
            @Override
            public int compare(ScheduledTask o1, ScheduledTask o2) {
                LocalTime t1 = o1.getTime()
                        .minusHours(currentTime.getHour())
                        .minusMinutes(currentTime.getMinute())
                        .minusSeconds(currentTime.getSecond())
                        .minusNanos(currentTime.getNano());
                LocalTime t2 = o2.getTime()
                        .minusHours(currentTime.getHour())
                        .minusMinutes(currentTime.getMinute())
                        .minusSeconds(currentTime.getSecond())
                        .minusNanos(currentTime.getNano());

                return t1.compareTo(t2);
            }
        });
        queue = new ConcurrentLinkedQueue<>(list);
        System.out.println(queue);
        execute();

    }

    public static void execute() {
        LocalTime now = LocalTime.now();
        ScheduledTask task = queue.poll();
        queue.add(task);
        LocalTime time = task.getTime();
        long ms;
        if (now.compareTo(time) < 0) {
            LocalTime delay = time
                    .minusHours(now.getHour())
                    .minusMinutes(now.getMinute())
                    .minusSeconds(now.getSecond())
                    .minusNanos(now.getNano());
            ms = delay.getHour() * 60L * 60 * 1000 +
                    delay.getMinute() * 60 * 1000 +
                    delay.getSecond() * 1000 +
                    delay.getNano() / 1000000;
        } else {
            ms = 0;
        }
        System.out.println(ms);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                doTask(task);
                execute();
            }
        }, ms);
    }

    public static void doTask(ScheduledTask task) {
        System.out.println(task);

    }


}
