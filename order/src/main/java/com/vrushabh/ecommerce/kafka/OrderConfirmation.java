package com.vrushabh.ecommerce.kafka;

import com.vrushabh.ecommerce.customer.CustomerResponse;
import com.vrushabh.ecommerce.order.PaymentMethod;
import com.vrushabh.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
