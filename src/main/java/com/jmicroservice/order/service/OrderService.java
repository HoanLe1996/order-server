package com.jmicroservice.order.service;

import com.jmicroservice.order.customer.CustomerClient;
import com.jmicroservice.order.dto.OrderRequest;
import com.jmicroservice.order.exception.BusinessException;
import com.jmicroservice.order.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;
    public Integer createOrder(OrderRequest orderRequest) {
        // check the customer
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID::"+ orderRequest.customerId()) );
        // purchase the products --> product-ms

        // persist order

        // persist order lines

        // start payment process

        // send the order confirmation --> notification-ms(kafka)
    }
}
