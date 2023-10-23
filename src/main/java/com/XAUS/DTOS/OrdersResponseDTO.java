package com.XAUS.DTOS;

import java.util.Date;
import java.util.List;

public class OrdersResponseDTO {

    private Long Id;
    private Long UserId;
    private String ClientName;
    private List<OrderProductDTO> Products;
    private String UserName;
    private Long ClientId;
    private String ClientCpf;
    private Boolean ItsPayed;
    private Float OrderPrice;

    private Long paymentMethodId;

    private String paymentMethod;

    private Date CreatedAt;

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public List<OrderProductDTO> getProducts() {
        return Products;
    }

    public void setProducts(List<OrderProductDTO> products) {
        Products = products;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Long getClientId() {
        return ClientId;
    }

    public void setClientId(Long clientId) {
        ClientId = clientId;
    }

    public String getClientCpf() {
        return ClientCpf;
    }

    public void setClientCpf(String clientCpf) {
        ClientCpf = clientCpf;
    }

    public Boolean getItsPayed() {
        return ItsPayed;
    }

    public void setItsPayed(Boolean itsPayed) {
        ItsPayed = itsPayed;
    }

    public Float getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(Float orderPrice) {
        OrderPrice = orderPrice;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
