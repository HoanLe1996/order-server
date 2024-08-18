package com.jmicroservice.order.product;


import com.jmicroservice.order.dto.PurchaseRequest;
import com.jmicroservice.order.dto.PurchaseResponse;
import com.jmicroservice.order.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(request, httpHeaders);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(productUrl + "/purchase",
                POST, requestEntity, responseType
        );
        if(responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the products purchase" + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

}
