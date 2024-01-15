package com.XAUS.Services.Payment;

import com.XAUS.Models.Payment.PaymentMethods;
import com.XAUS.Repositories.Payment.PaymentMethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodsService {

    @Autowired
    public PaymentMethodsRepository paymentMethodsRepository;

    public List<PaymentMethods> getAllPaymentMethods(){
        return this.paymentMethodsRepository.findAll();
    }

}
