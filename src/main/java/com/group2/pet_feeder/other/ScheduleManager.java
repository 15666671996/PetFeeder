package com.group2.pet_feeder.other;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.group2.pet_feeder.entity.Task;

import org.springframework.boot.web.client.RestTemplateBuilder;


public class ScheduleManager {

    private static List<Task> queue = Collections.synchronizedList(new LinkedList<Task>());

    private static Timer timer = new Timer();

    public static void init(List<Map<String, Object>> tasks) {
        LinkedList<Task> list = new LinkedList<>();
        for (Map<String, Object> task : tasks) {
            String[] time = ((String) task.get("time")).split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            list.add(new Task((String) task.get("userId"), LocalTime.of(hour, minute)));
        }
        LocalTime currentTime = LocalTime.now();
        list.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
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
        queue.addAll(list);

//        queue = Collections.synchronizedList(list);
        if (queue.size() > 0) {
            execute();
        }
    }

    public static void execute() {
        LocalTime now = LocalTime.now();
        Task task = queue.get(0);
        System.out.println(task);
        LocalTime time = task.getTime();
        LocalTime delay = time
                .minusHours(now.getHour())
                .minusMinutes(now.getMinute())
                .minusSeconds(now.getSecond())
                .minusNanos(now.getNano());
        long ms = delay.getHour() * 60L * 60 * 1000 +
                delay.getMinute() * 60 * 1000 +
                delay.getSecond() * 1000 +
                delay.getNano() / 1000000;
        System.out.println(queue);
        System.out.println("--------------------------------");
        System.out.println(task + "will be executed after" + ms / 1000 + "s");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("scheduled task executed id:" + task.getUserId() + "time:" + task.getTime());
                HttpClient client = new HttpClient(new RestTemplateBuilder());
                try {
                    client.serveFood(task.getUserId());
                } catch (Exception e) {
                    System.out.println("Error");
                }
                queue.remove(0);
                queue.add(task);
                execute();
            }
        }, ms);
    }


    public static void addTask(String userId, String time) {
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        Task newTask = new Task(userId, LocalTime.of(hour, minute));
        boolean added = false;
        for (int i = 0; i < queue.size(); i++) {

            LocalTime now = LocalTime.now(ZoneId.of("Europe/Dublin"));
            Task next = queue.get(i);
            LocalTime temp = next.getTime()
                    .minusHours(now.getHour())
                    .minusMinutes(now.getMinute())
                    .minusSeconds(now.getSecond())
                    .minusNanos(now.getNano());
            LocalTime newTime = newTask.getTime()
                    .minusHours(now.getHour())
                    .minusMinutes(now.getMinute())
                    .minusSeconds(now.getSecond())
                    .minusNanos(now.getNano());
            if (temp.compareTo(newTime) >= 0) {
                queue.add(i, newTask);
                added = true;
                if (i == 0) {
                    timer.cancel();
                    timer = new Timer();
                    execute();
                }
                break;
            }
        }
        if (!added) {
            queue.add(newTask);
        }
        System.out.println(queue);
    }

    public static void deleteTask(String userId, String time) {
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        LocalTime localTime = LocalTime.of(hour, minute);
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getUserId().equals(userId) && queue.get(i).getTime().compareTo(localTime) == 0) {
                queue.remove(i);
                if (i == 0) {
                    timer.cancel();
                    timer = new Timer();
                    if (queue.size() != 0) {
                        execute();
                    }
                }
                break;
            }
        }
        System.out.println(queue);
    }
}
