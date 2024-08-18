package com.jmicroservice.order.controller;


import com.jmicroservice.order.dto.OrderLineResponse;
import com.jmicroservice.order.dto.OrderRequest;
import com.jmicroservice.order.dto.OrderResponse;
import com.jmicroservice.order.service.OrderLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {
    private final OrderLineService orderLineService;

    @GetMapping("/order/{order-id}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderId(@PathVariable("order-id") Integer orderId) {
        return ResponseEntity.ok(orderLineService.findByOrderId(orderId));
    }
}
