package com.mindspark.family_finances.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home-page")
public class homePageController {

    @GetMapping
    public String homePage(){
        return "HomePage";
    }
}
