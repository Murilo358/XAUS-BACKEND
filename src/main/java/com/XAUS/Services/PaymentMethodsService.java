package com.XAUS.Services;

import com.XAUS.Models.PaymentMethods;
import com.XAUS.Repositories.PaymentMethodsRepository;
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
