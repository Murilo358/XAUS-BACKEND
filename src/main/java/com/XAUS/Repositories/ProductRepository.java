package com.XAUS.Repositories;

import com.XAUS.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//JpaRepository já trás todos os métodos de acesso ao db sem ter que implementar nada, alem do tipo do repository que ela retorna
//E o tipo do id

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
