package com.XAUS.Models.Orders;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import io.hypersistence.utils.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.*;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Table(name = "orders")
@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Convert(attributeName = "entityAttrName", converter = JsonNodeBinaryType.class)
@Getter
@Setter
@ToString
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "products", columnDefinition = "jsonb", nullable = false)
    private JsonNode products;

    @Column(name = "orderPrice")
    private  Float orderPrice;

    @Column(name = "itsPayed")
    private  Boolean itsPayed;

    @Column(name = "itsPackaged", columnDefinition = "boolean default false")
    private  Boolean itsPackaged;


    @Column(name= "paymentMethod")
    @JoinColumn(name = "id", referencedColumnName = "paymentMethod")
    private Long paymentMethod;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public Orders(Long userId, String userName, Long clientId, String clientCpf, String clientName,
                  JsonNode products, Float orderPrice, Boolean itsPayed, Long paymentMethod, Boolean packaged) {
        this.userId = userId;
        this.userName = userName;
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.products = products;
        this.orderPrice = orderPrice;
        this.itsPayed = itsPayed;
        this.paymentMethod = paymentMethod;
        this.itsPackaged = packaged;
    }


}
