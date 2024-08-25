package com.vrushabh.ecommerce.payment;

import com.vrushabh.ecommerce.customer.CustomerResponse;
import com.vrushabh.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
