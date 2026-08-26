package com.controller;

import com.agents.parallelmapper.BatchHoroscopeAgent;
import com.agents.parallelmapper.Person;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ParallelMapperAgentController {
    @Resource
    private BatchHoroscopeAgent agent;

    @GetMapping("/horoscope")
    public List<String> plan() {
        List<Person> persons = List.of(
                new Person("张三", "射手座"),
                new Person("李四", "金牛座"),
                new Person("王五", "狮子座"));
        return agent.generateHoroscopes(persons);
    }

}