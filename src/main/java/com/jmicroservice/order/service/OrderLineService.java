package com.jmicroservice.order.service;


import com.jmicroservice.order.dto.OrderLineRequest;
import com.jmicroservice.order.dto.OrderLineResponse;
import com.jmicroservice.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        return orderLineRepository.save(
                orderLineMapper.toOrderLine(orderLineRequest)
        ).getId();
    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
        return this.orderLineRepository
                .findByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse).collect(Collectors.toList());
    }
}
