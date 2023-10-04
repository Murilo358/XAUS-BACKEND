package com.XAUS.DTOS;

import java.util.List;

public interface OrdersConvertResponseDTO {

    Long getId();
    Long getUserId();
    String getClientName();
    List<OrderProductConvertResponseDTO> getProducts();
    String getUserName();
    Long getClientId();
    String getClientCpf();
    Boolean getItsPayed();
    Float getOrderPrice();
}



