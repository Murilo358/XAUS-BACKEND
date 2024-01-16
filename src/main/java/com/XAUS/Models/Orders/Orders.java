package com.XAUS.Models.Orders;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@Table(name = "orders")
@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
@Getter
@Setter
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


    @Column(name= "paymentMethod")
    @JoinColumn(name = "id", referencedColumnName = "paymentMethod")
    private Long paymentMethod;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public Orders(Long userId, String userName, Long clientId, String clientCpf, String clientName,
                  JsonNode products, Float orderPrice, Boolean itsPayed, Long paymentMethod) {
        this.userId = userId;
        this.userName = userName;
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.products = products;
        this.orderPrice = orderPrice;
        this.itsPayed = itsPayed;
        this.paymentMethod = paymentMethod;
    }


}
