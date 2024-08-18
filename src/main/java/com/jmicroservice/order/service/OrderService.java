package com.jmicroservice.order.service;

import com.jmicroservice.order.customer.CustomerClient;
import com.jmicroservice.order.dto.OrderLineRequest;
import com.jmicroservice.order.dto.OrderRequest;
import com.jmicroservice.order.dto.OrderResponse;
import com.jmicroservice.order.dto.PurchaseRequest;
import com.jmicroservice.order.exception.BusinessException;
import com.jmicroservice.order.kafka.OrderConfirmation;
import com.jmicroservice.order.kafka.OrderProducer;
import com.jmicroservice.order.payment.PaymentClient;
import com.jmicroservice.order.payment.PaymentRequest;
import com.jmicroservice.order.product.ProductClient;
import com.jmicroservice.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerClient customerClient;

    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest orderRequest) {
        // check the customer
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID::"+ orderRequest.customerId()) );
        // purchase the products --> product-ms
        var purchasedProducts = this.productClient.purchaseProducts(orderRequest.products());
        // persist order
        var order = this.orderRepository.save(mapper.toOrder(orderRequest));
        // persist order lines
        for(PurchaseRequest purchaseRequest: orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()

                    )
            );
        }
        // TODO start payment process
        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        // send the order confirmation --> notification-ms(kafka)
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return this.orderRepository.findAll().stream().map(mapper::fromOrder).collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return this.orderRepository
                .findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id:" + orderId));
    }
}
