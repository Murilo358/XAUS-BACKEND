package com.XAUS.Repositories;

import com.XAUS.DTOS.ClientsReportDTO;
import com.XAUS.DTOS.OrdersUsersReportDTO;
import com.XAUS.DTOS.ProductsReportsReponseDTO;
import com.XAUS.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportsRepository extends JpaRepository<Orders,Long> {

    @Query(value = "select count(*) from clients where created_at between CAST( :lastMonth AS timestamp)  AND  CAST( :currentDate AS timestamp) ", nativeQuery = true)
    public Integer getLastClients(@Param("lastMonth") String lastMonth, @Param("currentDate") String currentDate);

    @Query(value = "select count(*) from clients ", nativeQuery = true)
    public Integer getAllClientsCount();

    @Query(value = "select count(*) from users", nativeQuery = true)
    public Integer getLastUsers(@Param("lastMonth") String lastMonth, @Param("currentDate") String currentDate);

    @Query(value = "select count(*) from users ", nativeQuery = true)
    public Integer getAllUsers();


    @Query(value = "select count(*) from orders where created_at between CAST( :lastMonth AS timestamp)  AND  CAST( :currentDate AS timestamp)", nativeQuery = true)
    public Integer getLattestOrders(@Param("lastMonth") String lastMonth, @Param("currentDate") String currentDate);

    @Query(value = "select * from orders order by created_at desc limit 3", nativeQuery = true)
    public List<Orders> getLastThreeOrders();

    @Query(value = "SELECT u.name, count(o.*) FROM orders as o  left join users AS u ON u.id = user_id WHERE o.created_at between CAST( :lastMonth AS timestamp)  AND  CAST( :currentDate AS timestamp)  GROUP BY u.name;", nativeQuery = true)
    public List<OrdersUsersReportDTO> getOrdersByUser(@Param("lastMonth") String lastMonth, @Param("currentDate") String currentDate);

    @Query(value = "SELECT products->>'productName' as productName,SUM(CAST(products->>'buyedQuantity' AS integer)) as quantity FROM ( SELECT jsonb_array_elements(products) as products FROM orders ) as subquery GROUP BY productName", nativeQuery = true)
    public List<ProductsReportsReponseDTO> getProductsReport();
}
