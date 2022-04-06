package com.group2.pet_feeder.other;

import com.group2.pet_feeder.entity.Task;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ScheduleManager {

    private static List<Task> queue;

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
        queue = Collections.synchronizedList(list);
        System.out.println(queue);
        execute();

    }

    public static void execute() {
        LocalTime now = LocalTime.now();
        Task task = queue.get(0);
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
                System.out.println(task);
                execute();
            }
        }, ms);
    }


    public static void addTask(String userId, String time) {
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        Task newTask = new Task(userId, LocalTime.of(hour, minute));
        for (int i = 0; i < queue.size(); i++) {

            LocalTime now = LocalTime.now();
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
                queue.add(i + 1, newTask);
                break;
            }
        }
        System.out.println(queue);
    }

    public static void deleteTask(String userId, String time) {
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        LocalTime localTime = LocalTime.of(hour, minute);
        Iterator<Task> iterator = queue.iterator();
        while (iterator.hasNext()){
            Task next = iterator.next();
            if(next.getUserId().equals(userId)&&next.getTime().compareTo(localTime)==0){
                iterator.remove();
                break;
            }
        }
        System.out.println(queue);
    }
}
