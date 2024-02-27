package com.XAUS.Controllers.Reports;

import com.XAUS.DTOS.Reports.DashboardReportDTO;
import com.XAUS.Services.Reports.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reports")
public class ReportsController {

    @Autowired
    public ReportsService reportsService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardReportDTO> getDashBoardReport (){

        return ResponseEntity.ok(reportsService.getDashboardreport());
    }

}
