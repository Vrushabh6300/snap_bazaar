package com.vrushabh.ecommerce.order;

import com.vrushabh.ecommerce.customer.CustomerClient;
import com.vrushabh.ecommerce.exception.BusinessException;
import com.vrushabh.ecommerce.kafka.OrderConfirmation;
import com.vrushabh.ecommerce.kafka.OrderProducer;
import com.vrushabh.ecommerce.orderline.OrderLineRequest;
import com.vrushabh.ecommerce.orderline.OrderLineService;
import com.vrushabh.ecommerce.payment.PaymentClient;
import com.vrushabh.ecommerce.payment.PaymentRequest;
import com.vrushabh.ecommerce.product.ProductClient;
import com.vrushabh.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    public Integer createOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order for provided customerId " + request.customerId()));
        var purchasedProducts = productClient.purchaseProducts(request.products());
        var order = repository.save(mapper.toOrder(request));
        for(PurchaseRequest purchaseRequest: request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity())
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No order found with order ID : " + orderId));
    }
}
