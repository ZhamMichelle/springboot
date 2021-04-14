package com.learning.springboot.learn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController  //помечая класс как контроллер, где каждый метод возвращает объект вместо представления(view). Это сокращение для @Controller и @ResponseBody, вместе взятых.
public class GreetingController {
    private static final String template = "Hello, %s!";
    private static AtomicLong counter=new AtomicLong();


    @RequestMapping("/mapping")
    public Greeting greeting(@RequestParam(value = "name", required = false, defaultValue = "world") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template,name));
    }
}
