package com.XAUS.Repositories.Payment;

import com.XAUS.Models.Payment.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods,Long>  {
}
