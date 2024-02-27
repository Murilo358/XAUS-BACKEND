package com.XAUS.DTOS.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Boolean itsPackaged;

}
