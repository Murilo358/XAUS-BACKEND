package com.XAUS.Models;

import com.XAUS.DTOS.ProductRequestDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "Products")
@Entity(name = "Products")
//Usando a biblioteca lombok ele gera em tempo
//de runtime os getters e constructor
//Construtor sem argumento
//Construtor com todos argumentos
//Indica a representação unica dessa classe (deixa o objeto inmutavel)
//@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "quantity")
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Product(ProductRequestDTO data) {
        this.name = data.name();
        this.description = data.description();
        this.price = data.price();
        this.quantity = data.quantity();
    }


}
