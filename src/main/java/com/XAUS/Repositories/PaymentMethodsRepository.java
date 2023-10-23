package com.XAUS.Repositories;

import com.XAUS.Models.PaymentMethods;
import com.XAUS.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods,Long>  {
}
