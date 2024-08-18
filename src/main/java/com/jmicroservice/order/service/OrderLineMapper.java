package com.jmicroservice.order.service;

import com.jmicroservice.order.dto.OrderLineRequest;
import com.jmicroservice.order.dto.OrderLineResponse;
import com.jmicroservice.order.model.Order;
import com.jmicroservice.order.model.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .productId(orderLineRequest.productId())
                .order(Order.builder().id(orderLineRequest.orderId()).build())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(), orderLine.getQuantity());
    }
}
