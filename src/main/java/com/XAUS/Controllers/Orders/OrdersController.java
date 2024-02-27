package com.XAUS.Controllers.Orders;

import com.XAUS.DTOS.Orders.OrderRequestDTO;
import java.util.List;

import com.XAUS.DTOS.Orders.OrdersResponseDTO;
import com.XAUS.DTOS.Products.ProductsReportsReponseDTO;
import com.XAUS.Models.Orders.Orders;
import com.XAUS.Services.Orders.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    public OrdersService ordersService;

    @PostMapping("/create")
    public ResponseEntity<Orders> createNewOrder(@RequestBody OrderRequestDTO data){
        return ResponseEntity.ok(ordersService.newOrder(data));

    }
    @GetMapping("/{orderId}")
    public  ResponseEntity<List<OrdersResponseDTO>> getOrderBYId(@PathVariable Long orderId){
        return ResponseEntity.ok(ordersService.findById(orderId));
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<OrdersResponseDTO>> getOrdersByUserId(@PathVariable Long userId){
        return ResponseEntity.ok( ordersService.OrdersByUserId(userId));

    }

    @GetMapping("/byclient/{clientId}")
    public ResponseEntity<List<OrdersResponseDTO>> OrdersByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(ordersService.OrdersByClientId(clientId));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<OrdersResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @PostMapping("{orderId}/setPayed")
    public ResponseEntity<String> setOrderPayed(@PathVariable Long orderId){
        ordersService.setOrderPayed(orderId);
        return ResponseEntity.ok("Order with id " + orderId + " set isPayed to true successfully!");
    }

    @GetMapping("productsReport")
    public ResponseEntity<List<ProductsReportsReponseDTO>> getProductsReport(){
        return ResponseEntity.ok(ordersService.getProductsReport());
    }

    @PostMapping("{orderId}/setPackaged")
    public ResponseEntity<String> setOrderItsPackaged(@PathVariable Long orderId, @RequestBody Boolean setPackaged){
        ordersService.setOrderPackaged(orderId, setPackaged);
        return ResponseEntity.ok("Order with id " + orderId + " set isPackaged to " + setPackaged + " successfully!");
    }

}
