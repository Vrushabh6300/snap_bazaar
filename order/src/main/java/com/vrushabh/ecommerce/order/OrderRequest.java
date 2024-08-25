package com.vrushabh.ecommerce.order;

import com.vrushabh.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be precise")
        PaymentMethod paymentMethod,
        @NotNull(message = "Payment method should be precise")
        @NotEmpty(message = "Payment method should be precise")
        @NotBlank(message = "Payment method should be precise")
        String customerId,
        @NotEmpty(message = "At least one product is required")
        List<PurchaseRequest> products
) {
}
