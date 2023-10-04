package com.XAUS.DTOS;

public class OrderProductDTO {

    public String productName;
    private Float productPrice;
    private Integer buyedQuantity;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getBuyedQuantity() {
        return buyedQuantity;
    }

    public void setBuyedQuantity(Integer buyedQuantity) {
        this.buyedQuantity = buyedQuantity;
    }
}
