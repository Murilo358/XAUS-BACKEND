package com.XAUS.DTOS;

import java.util.List;

public record DashboardReportDTO (List<ProductsReportsReponseDTO> lastProducts, ClientsReportDTO  clientsReport, LattestOrdersReportDTO ordersReports, List<OrdersUsersReportDTO> ordersByUser, UsersReportDTO usersReport) {
}
