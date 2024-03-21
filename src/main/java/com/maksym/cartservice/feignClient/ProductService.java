package com.maksym.cartservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-SERVICE")
public interface ProductService {
    @GetMapping("/api/products/exists/{id}")
    public ResponseEntity<Boolean> existsProduct(@PathVariable("id") Long id);
}
