package com.XAUS.Services.Products;

import com.XAUS.DTOS.Products.ProductRequestDTO;
import com.XAUS.DTOS.Products.ProductResponseDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Models.Products.Product;
import com.XAUS.Repositories.Products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    public ProductRepository repository;

    @Cacheable(value = "AllProducts" , key = "'all'")
    public List<ProductResponseDTO> getAll() {
        return repository.findAll().stream().map(ProductResponseDTO::new).toList();
    }

    @CacheEvict(value = "AllProducts", allEntries = true)
    public Product saveProduct(@RequestBody ProductRequestDTO data) {

        if(data.name() == null || data.description() == null || data.price()  == null || data.quantity() == null){

            throw new CustomException("Nenhum campo pode ser vázio", HttpStatus.BAD_GATEWAY);
        }

        Product productData = new Product(data);
        return repository.save(productData);

    }

    public Product findById(Long id ){

        Product product = this.repository.findById(id).orElse(null);
        if(product == null){
            throw new CustomException("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return product;
    }

    public ResponseEntity deleteProduct(Long id){

        //Just checking if isn't null
        this.findById(id);

        repository.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    @CacheEvict(value = "AllProducts", allEntries = true)
    public ResponseEntity updateProduct(Long id, @RequestBody ProductRequestDTO newData){

        Product product = this.findById(id);


        product.setName(newData.name());
        product.setDescription(newData.description());
        product.setPrice(newData.price());
        product.setQuantity(newData.quantity());
        repository.save(product);
        return ResponseEntity.ok().build();
    }

    @CacheEvict(value = "AllProducts", allEntries = true)
    public void addStock (Long id, Integer quantity){

        Product product = this.findById(id);

        product.setQuantity(product.getQuantity() + quantity );
        repository.save(product);

    }


}
