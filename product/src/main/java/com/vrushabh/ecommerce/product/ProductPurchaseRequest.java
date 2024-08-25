package com.vrushabh.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "ProductId is mandatory")
        Integer productId,
        @NotNull(message = "Product quantity is mandatory")
        double quantity
) {
}
