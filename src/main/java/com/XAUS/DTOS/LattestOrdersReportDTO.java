package com.XAUS.DTOS;

import com.XAUS.Models.Orders;

import java.util.List;

public record LattestOrdersReportDTO(Integer newOrders, Integer allOrders, List<Orders> lastThreeOrders) {
}
