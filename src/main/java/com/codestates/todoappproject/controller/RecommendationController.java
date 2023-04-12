package com.codestates.todoappproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    @GetMapping("/")
    public String helloWorld() {
        return "To-do Application";
    }
}
