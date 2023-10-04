package com.XAUS.Models;


import com.XAUS.DTOS.OrderRequestDTO;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Table(name = "orders")
@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private  String userName;


    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_cpf")
    private  String clientCpf;

    @Column(name = "client_name ")
    private  String clientName;

    @Type(type = "jsonb-node")
    @Column(name = "products", columnDefinition = "jsonb", nullable = false)
    private JsonNode products;

    @Column(name = "orderPrice")
    private  Float orderPrice;

    @Column(name = "itsPayed")
    private  Boolean itsPayed;

    public Orders(Long userId, String userName, Long clientId, String clientCpf, String clientName, JsonNode products, Float orderPrice, Boolean itsPayed) {
        this.userId = userId;
        this.userName = userName;
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.products = products;
        this.orderPrice = orderPrice;
        this.itsPayed = itsPayed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setProducts(JsonNode products) {
        this.products = products;
    }

    public void setOrderPrice(Float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setItsPayed(Boolean itsPayed) {
        this.itsPayed = itsPayed;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public String getClientName() {
        return clientName;
    }

    public JsonNode getProducts() {
        return products;
    }

    public Float getOrderPrice() {
        return orderPrice;
    }

    public Boolean getItsPayed() {
        return itsPayed;
    }

}
