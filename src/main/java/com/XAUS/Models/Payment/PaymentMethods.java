package com.XAUS.Models.Payment;

import lombok.*;

import javax.persistence.*;

@Table(name = "payment_methods")
@Entity(name = "payment_methods")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paymentMethod")
    private String paymentMethod;

}
