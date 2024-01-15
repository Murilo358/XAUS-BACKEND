package com.XAUS.DTOS.Reports;

import com.XAUS.Models.Orders.Orders;

import java.util.List;

public record LattestOrdersReportDTO(Integer newOrders, Integer allOrders, List<Orders> lastThreeOrders) {
}
