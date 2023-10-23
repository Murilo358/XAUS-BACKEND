package com.XAUS.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reports")
public class Reports {

    @GetMapping("/dashboard")
    public List getDashboardreport (){
        return ;
    }

}
