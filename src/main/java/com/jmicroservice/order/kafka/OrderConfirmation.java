package com.jmicroservice.order.kafka;

import com.jmicroservice.order.dto.CustomerResponse;
import com.jmicroservice.order.dto.PurchaseResponse;
import com.jmicroservice.order.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> purchases
) {
}
