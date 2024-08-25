package com.vrushabh.ecommerce.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .amount(request.amount())
                .build();
    }
}
