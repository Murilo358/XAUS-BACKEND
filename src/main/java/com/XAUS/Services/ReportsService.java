package com.XAUS.Services;

import com.XAUS.DTOS.ClientsReportDTO;
import com.XAUS.DTOS.DashboardReportDTO;
import com.XAUS.DTOS.LattestOrdersReportDTO;
import com.XAUS.DTOS.UsersReportDTO;
import com.XAUS.Repositories.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;

@Service
public class ReportsService {

    @Autowired
    public ReportsRepository reportsRepository;

    public DashboardReportDTO getDashboardreport(){

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 1);
        String currentDate =  (now.get(Calendar.MONTH) + 1) + "-"
                + now.get(Calendar.DATE ) + "-" + now.get(Calendar.YEAR) ;

        int month = now.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR);
        if(month == 0 ){
            month = 12;
            year = year - 1;
        }

        String lastMonth = (month) + "-"
                + now.get(Calendar.DATE) + "-" + year;

        return new DashboardReportDTO(
                this.reportsRepository.getProductsReport(),
                new ClientsReportDTO(
                        this.reportsRepository.getAllClientsCount(),
                        this.reportsRepository.getLastClients(lastMonth,currentDate)
                ),
                new LattestOrdersReportDTO(
                        this.reportsRepository.getLattestOrders(lastMonth,currentDate),
                        (int) this.reportsRepository.count(),
                        this.reportsRepository.getLastThreeOrders()
                ),
                this.reportsRepository.getOrdersByUser(lastMonth,currentDate),
                new UsersReportDTO(
                        this.reportsRepository.getAllUsers(),
                        this.reportsRepository.getLastUsers(lastMonth,currentDate)
                )
        );
    }

}
