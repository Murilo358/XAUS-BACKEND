package com.XAUS.DTOS.Reports;

import com.XAUS.DTOS.Clients.ClientsReportDTO;
import com.XAUS.DTOS.Orders.OrdersUsersReportDTO;
import com.XAUS.DTOS.Products.ProductsReportsReponseDTO;
import com.XAUS.DTOS.Users.UsersReportDTO;

import java.util.List;

public record DashboardReportDTO (List<ProductsReportsReponseDTO> lastProducts, ClientsReportDTO clientsReport, LattestOrdersReportDTO ordersReports, List<OrdersUsersReportDTO> ordersByUser, UsersReportDTO usersReport) {
}
