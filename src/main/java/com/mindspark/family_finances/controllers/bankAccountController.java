package com.mindspark.family_finances.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-account")
public class bankAccountController {

    @GetMapping
    public String homePage(){
        return "bank Account";
    }
}
