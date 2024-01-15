package com.XAUS.Services.Orders;

import com.XAUS.DTOS.Orders.OrderProductDTO;
import com.XAUS.DTOS.Orders.OrderRequestDTO;
import com.XAUS.DTOS.Orders.OrdersConvertResponseDTO;
import com.XAUS.DTOS.Orders.OrdersResponseDTO;
import com.XAUS.DTOS.Products.ProductsReportsReponseDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Exceptions.OutOfStockException;
import com.XAUS.Models.Clients.Clients;
import com.XAUS.Models.Orders.Orders;
import com.XAUS.Models.Products.Product;
import com.XAUS.Models.User.User;
import com.XAUS.Utils.MapperUtils;
import com.XAUS.Repositories.Clients.ClientsRepository;
import com.XAUS.Repositories.Orders.OrdersRepository;
import com.XAUS.Repositories.Products.ProductRepository;
import com.XAUS.Repositories.User.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    public OrdersRepository repository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public ClientsRepository clientsRepository;
    @Autowired
    public UserRepository userRepository;

    private final ObjectMapper mapper = MapperUtils.createMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Orders newOrder(@RequestBody OrderRequestDTO data) {

//       =-=-=-===-=-=-=-=TESTE DE POST=-=-=-===-=-=-=-=
//                localhost:8080/orders/create

//        {
//                "userId": 10, (coloque o id do usuário desejado)
//                "clientId": 1,  (coloque o id do cliente desejado, se for null será o 1(não cadastrado))
//                "products": [[1,2], [1,10]] (coloque o id do produto, quantidade a ser comprada)
//        }

        User user = this.userRepository.findById(data.userId()).orElse(null);

        Clients client = this.clientsRepository.findById(data.clientId() != null ?data.clientId() : 1L).orElse(null);

        if (user == null){
            throw new CustomException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        List<List<Integer>> products = data.products();

        AtomicReference<Float> orderPrice = new AtomicReference<>(0.0F);


        List<JsonNode> findedProducts = products.stream()
                .map(produtoInfo -> {
                    long productId = produtoInfo.get(0);
                    int quantity = produtoInfo.get(1);

                    Optional<Product> productOpt = this.productRepository.findById(productId);
                    if (productOpt.isPresent()) {
                        Product product = productOpt.get();
                        try{
                            if (product.getQuantity() >= quantity) {
                                product.setQuantity(product.getQuantity() - quantity);
                                orderPrice.updateAndGet( v -> Float.sum(v , (product.getPrice() * quantity )) );

                                OrderProductDTO orderProduct = new OrderProductDTO();
                                orderProduct.setProductName(product.getName());
                                orderProduct.setProductPrice(product.getPrice());
                                orderProduct.setBuyedQuantity(quantity);
                                return mapper.convertValue(orderProduct, JsonNode.class);
                             }
                            else{
                                throw new RuntimeException();
                            }
                        }catch(Exception e){
                            throw new OutOfStockException("Não há estoque suficiente para o produto "+ product.getName() + " selecionado.", HttpStatus.BAD_GATEWAY);
                        }

                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        //Converting list of JsonNode to ArrayNode

        ArrayNode productsArray = mapper.createArrayNode();

        for (JsonNode productNode : findedProducts) {
            productsArray.add(productNode);
        }


            Orders newOrder = new Orders(data.userId(), user.getName(), client.getId(), client.getCpf(), client.getName(),productsArray,orderPrice.get(),false, data.paymentMethod()  );

        return repository.save(newOrder);
    }


    public  List<OrdersResponseDTO> findById(Long orderID){

        List<OrdersConvertResponseDTO> order =  repository.findBySomething(orderID, "order");

        if (order == null) {

            throw new CustomException("Pedido não encontrado.",  HttpStatus.NOT_FOUND);
        }

        return this.prepareData(order);
    }

    public List<OrdersResponseDTO> OrdersByUserId(Long id){

        List<OrdersConvertResponseDTO> orders = repository.findBySomething(id, "user");

        if (orders.isEmpty()){
            throw new CustomException("Nenhum pedido encontrado. ",  HttpStatus.NOT_FOUND);
        }

        return this.prepareData(orders);
    }

    public List<OrdersResponseDTO> prepareData(List<OrdersConvertResponseDTO> orders){

        return orders.stream()
                .map(order -> {
                    OrdersResponseDTO dto = new OrdersResponseDTO();
                    dto.setUserName(order.getUserName());
                    dto.setUserId(order.getUserId());
                    dto.setClientName(order.getClientName());
                    dto.setItsPayed(order.getItsPayed());
                    dto.setClientId(order.getClientId());
                    dto.setOrderPrice(order.getOrderPrice());
                    dto.setClientCpf(order.getClientCpf());
                    dto.setId(order.getId());
                    dto.setPaymentMethodId(order.getPaymentMethodId());
                    dto.setPaymentMethod(order.getPaymentMethod());
                    dto.setCreatedAt(order.getCreatedAt());

                    List<OrderProductDTO> productDTOs = repository.findProductsBySomething(order.getId()).stream()
                            .map(product -> {
                                OrderProductDTO productDTO = new OrderProductDTO();
                                productDTO.setProductName(product.getProductName());
                                productDTO.setProductPrice(product.getProductPrice());
                                productDTO.setBuyedQuantity(product.getBuyedQuantity());
                                return productDTO;
                            })
                            .collect(Collectors.toList());

                    dto.setProducts(productDTOs);

                    return dto;
                })
                .collect(Collectors.toList());


    }

    public List<OrdersResponseDTO> OrdersByClientId(Long id){

        List<OrdersConvertResponseDTO> orders = repository.findBySomething(id, "client");


        if (orders.isEmpty()){
            throw new CustomException("Nenhum pedido encontrado. ",  HttpStatus.NOT_FOUND);
        }

        return this.prepareData(orders);

    }

    public List<OrdersResponseDTO> getAllOrders(){

        List<OrdersConvertResponseDTO> orders = repository.findBySomething(1L, "nothing");

        if (orders.isEmpty()){
            throw new CustomException("Nenhum pedido encontrado. ",  HttpStatus.NOT_FOUND);
        }

        return this.prepareData(orders);

    }
    public List<ProductsReportsReponseDTO> getProductsReport (){
        return this.repository.getProductsReport();
    }

    public ResponseEntity setOrderPayed(Long orderId){

        Optional<Orders> order = repository.findById(orderId);

        if (order.isPresent()) {
            Orders orders = order.get();
            orders.setItsPayed(Boolean.TRUE);
            repository.save(orders);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }


    }


}
