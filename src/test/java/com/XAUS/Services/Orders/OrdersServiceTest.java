package com.XAUS.Services.Orders;

import com.XAUS.DTOS.Orders.OrderRequestDTO;
import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Exceptions.OutOfStockException;
import com.XAUS.Models.Clients.Clients;
import com.XAUS.Models.Products.Product;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Models.User.User;
import com.XAUS.Notifications.Orders.Publisher.OrdersPublisher;
import com.XAUS.Repositories.Orders.OrdersRepository;
import com.XAUS.Repositories.Products.ProductRepository;
import com.XAUS.Services.Clients.ClientsService;
import com.XAUS.Services.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private User createUser(long id) {
        UserRequestDTO userData = new UserRequestDTO("Teste", "458.388.978-05",
                "murilobarbosa358@,gmail.com", new Date(), "123456", UserRole.ADMIN);
        return new User(userData, true);
    }

    private Product createProduct(long id, int quantity) {
        return new Product(id, "Test", "Test description", 10.00F, quantity);
    }

    private Clients createClient(long id) {
        return new Clients(id, new Date(), "emailteste@gmail.com", "Murilo", "458.388.978-05");
    }

    private UserRequestDTO createUserRequest(){
        return new UserRequestDTO("Teste", "458.388.978-05",
                "murilobarbosa358@,gmail.com", new Date(), "123456", UserRole.ADMIN);
    }

    private List<List<Integer>> createProductListWithQuantity(long productId, int quantity) {
        List<List<Integer>> productList = new ArrayList<>();
        List<Integer> productAndQuantity = new ArrayList<>();
        productAndQuantity.add((int) productId);
        productAndQuantity.add(quantity);
        productList.add(productAndQuantity);
        return productList;
    }


    @Test
    @DisplayName("Should throws a CustomException(NOT FOUND) when the user id wasn't found ")
    void shouldThrowExceptionWhenUserNotFoundForNewOrder() {

        long userId = 10L;
        OrderRequestDTO data = new OrderRequestDTO(userId, null, createProductListWithQuantity(1L, 1), 1L);
        when(userService.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomException.class, ()-> ordersService.newOrder(data));

        assertThat(exception).isNotNull();
    }


    @Test
    @DisplayName("Should throws a CustomException(NOT FOUND) when the client wasnt found")
    void shouldThrowExceptionWhenClientNotFoundForNewOrder(){

        long productId = 10L;
        long clientId = 1L;
        OrderRequestDTO data = new OrderRequestDTO(10L, null, createProductListWithQuantity(productId, 10), clientId);
        User user = createUser(10L);
        Product product = createProduct(productId, 5);

        when(userService.findById(10L)).thenReturn(Optional.of(user));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        assertThrows(CustomException.class, () -> ordersService.newOrder(data), "Cliente não encontrado");

    }

    @Test
    @DisplayName("Should throws a CustomException(NOT FOUND) when the product id wasn't found ")
    void shouldThrowExceptionWhenProductNotFoundForNewOrder() {

        long productId = 10L;
        long clientId = 1L;
        OrderRequestDTO data = new OrderRequestDTO(10L, null, createProductListWithQuantity(productId, 10), clientId);
        User user = createUser(10L);
        Clients client = createClient(1L);

        when(userService.findById(10L)).thenReturn(Optional.of(user));
        when(clientsService.findByIdWithoutError(1L)).thenReturn(Optional.of(client));

        assertThrows(CustomException.class, () -> ordersService.newOrder(data), "not found");
    }


    @Test
    @DisplayName("Should throws a OutOfStockException(BAD GATEWAY) when the order product quantity its bigger than the product quantity")
    void shouldThrowExceptionWhenProductIsOutOfStockForNewOrder(){


        long productId = 10L;
        long clientId = 1L;
        OrderRequestDTO data = new OrderRequestDTO(10L, null, createProductListWithQuantity(productId, 10), clientId);
        User user = createUser(10L);
        Clients client = createClient(1L);
        Product product = createProduct(10L, 5);

        when(userService.findById(10L)).thenReturn(Optional.of(user));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(clientsService.findByIdWithoutError(1L)).thenReturn(Optional.of(client));

        assertThrows(OutOfStockException.class, () -> ordersService.newOrder(data), "Não há estoque suficiente para o produto");
    }

    @Test
    @Disabled
    @DisplayName("Should succeed on creating a new order")
    void shouldSucceedWhenCreatingANewOrderTo (){

    }

}