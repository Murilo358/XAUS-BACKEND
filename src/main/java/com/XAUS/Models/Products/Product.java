package com.XAUS.Models.Products;

import com.XAUS.DTOS.Products.ProductRequestDTO;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Table(name = "Products")
@Entity(name = "Products")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "price")
    private Float price;
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    public Product(ProductRequestDTO data) {
        this.name = data.name();
        this.description = data.description();
        this.price = data.price();
        this.quantity = data.quantity();
    }


}
