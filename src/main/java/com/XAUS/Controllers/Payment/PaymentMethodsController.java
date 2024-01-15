package com.XAUS.Controllers.Payment;

import com.XAUS.Models.Payment.PaymentMethods;
import com.XAUS.Services.Payment.PaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("payments")
public class PaymentMethodsController {


    @Autowired
    public PaymentMethodsService paymentMethodsService;

    @GetMapping("getAll")
    public List<PaymentMethods> getAllPaymentMethods(){
        return this.paymentMethodsService.getAllPaymentMethods();
    }
}
