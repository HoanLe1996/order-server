package com.jmicroservice.order.payment;

import com.jmicroservice.order.dto.CustomerResponse;
import com.jmicroservice.order.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
