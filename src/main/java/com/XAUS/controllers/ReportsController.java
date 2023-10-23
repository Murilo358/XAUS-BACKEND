package com.XAUS.controllers;

import com.XAUS.DTOS.DashboardReportDTO;
import com.XAUS.Services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportsController {

    @Autowired
    public ReportsService reportsService;

    @GetMapping("/dashboard")
    public DashboardReportDTO getDashboardreport (){

        return this.reportsService.getDashboardreport();
    }

}
