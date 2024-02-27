package com.XAUS.Controllers.Products;


import com.XAUS.DTOS.Products.ProductRequestDTO;
import com.XAUS.DTOS.Products.ProductResponseDTO;
import com.XAUS.Models.Products.Product;
import com.XAUS.Services.Products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {


    @Autowired
    public ProductService productService;


    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(this.productService.getAll());

    }


    @PostMapping("/create")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequestDTO data) {
        return ResponseEntity.ok(this.productService.saveProduct(data));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        this.productService.deleteProduct(productId);

        return ResponseEntity.ok().body("Product with id: " + productId + "  deleted successfully");

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO newData){
       productService.updateProduct(productId, newData);
        return ResponseEntity.ok().body("Product with id: " + productId + " updated successfully");
    }

    @PutMapping("/addStock/{productId}/{quantity}")
    public  ResponseEntity<String> addStock(@PathVariable Long productId, @PathVariable Integer quantity) {
        this.productService.addStock(productId, quantity);
        return ResponseEntity.ok().body("Stock added to product id:" + productId + " successfully");
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id ){
        return this.productService.findById(id);
    }


}
