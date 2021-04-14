package com.learning.springboot.shedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@EnableScheduling //гарантирует, что фоновые задачи создадуться
public class SheduledTasks {
    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000) //когда конкретный метод будет запущен.
    public void reportCurrentDate(){
        System.out.println("Time now is " + dateFormat.format(new Date()));
    }
}
