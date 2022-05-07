package com.arme.demo01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "greeting")
public class GreetingController {

    private final AtomicLong count = new AtomicLong();
    private static final String template ="hello %s";

    @GetMapping
    public Greeting saludar(@RequestParam(value = "name",defaultValue = "World") String name){
        return new Greeting(count.incrementAndGet(),String.format(template, name));
    }

}
