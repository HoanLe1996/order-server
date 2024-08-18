package com.jmicroservice.order.handler;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class ErrorResponse {
    Map<String, String> errors;
}
