package com.XAUS.Controllers.Products;


import com.XAUS.DTOS.Products.ProductRequestDTO;
import com.XAUS.DTOS.Products.ProductResponseDTO;
import com.XAUS.Models.Products.Product;
import com.XAUS.Services.Products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Informa que é um controller de request
@RestController
//Mapeia e request de product
@RequestMapping("products")
public class ProductController {

    //AutoWired injeta as depencias(instancia automacicamente uma classe)
    @Autowired
    public ProductService productService;


    @GetMapping("/getAll")
    public List<ProductResponseDTO> getAll() {

        return this.productService.getAll();

    }


    @PostMapping("/create")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequestDTO data) {
        Product savedProduct = this.productService.saveProduct(data);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId){

        return this.productService.deleteProduct(productId);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO newData){
       return this.productService.updateProduct(productId, newData);
    }

    @PutMapping("/addStock/{productId}/{quantity}")
    public ResponseEntity addStock(@PathVariable Long productId, @PathVariable Integer quantity) {
        this.productService.addStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id ){
        return this.productService.findById(id);
    }


}
