package com.XAUS.Services.Orders;

import com.XAUS.DTOS.Orders.OrderRequestDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Notifications.Orders.Publisher.OrdersPublisher;
import com.XAUS.Repositories.Orders.OrdersRepository;
import com.XAUS.Repositories.Products.ProductRepository;
import com.XAUS.Services.Clients.ClientsService;
import com.XAUS.Services.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OrdersServiceTest {

    @Autowired
    @InjectMocks
    OrdersService ordersService;
    @Mock
    public OrdersRepository repository;
    @Mock
    public ProductRepository productRepository;
    @Mock
    public ClientsService clientsService;
    @Mock
    public UserService userService;
    @Mock
    public OrdersPublisher ordersPublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Case none user id sent, then will be: CustomException(not found)")
    void newOrderCaseNoUserERROR() {
        List<List<Integer>> ProductsList = new ArrayList<>();

        List<Integer> productsAndQuantity =new ArrayList<>();
        productsAndQuantity.add(1);
        productsAndQuantity.add(1);

        ProductsList.add(productsAndQuantity);

        OrderRequestDTO data = new OrderRequestDTO(10L, null,ProductsList, 1L );

        when(userService.findById(10L)).thenReturn(Optional.empty());

//         Orders orders = ordersService.newOrder(data);
        Exception exception = assertThrows(CustomException.class, ()-> ordersService.newOrder(data));
        assertThat(exception).isNotNull();
    }



    @Test
    @DisplayName("Case none client id sent, then will be 1F")
    void newOrderCaseNoClientERROR() {
        List<List<Integer>> ProductsList = new ArrayList<>();

        List<Integer> productsAndQuantity =new ArrayList<>();
        productsAndQuantity.add(1);
        productsAndQuantity.add(1);

        ProductsList.add(productsAndQuantity);

        OrderRequestDTO data = new OrderRequestDTO(10L, null,ProductsList, 1L );

        when(clientsService.findByIdWithoutError(10L)).thenReturn(Optional.empty());

//         Orders orders = ordersService.newOrder(data);
        Exception exception = assertThrows(CustomException.class, ()-> ordersService.newOrder(data));
        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("Case product id wasn't found, then will be: CustomException(not found)")
    void newOrderCaseProductNotfoundERROR(){

    }

    @Test
    @DisplayName("Case product quantity wasn't bigger or equals to the sent quantity, then will be: CustomException(not found)")
    void newOrderCaseOutOfStockERROR(){

    }

}