package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.feignClient.ProductService;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.service.CartItemServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemController {

    private final CartItemServiceImpl cartItemService;

    public CartItemController(CartItemServiceImpl cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return new ResponseEntity<>(cartItemService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        return new ResponseEntity<>(cartItemService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody @Valid CartItemRequest cartItemRequest) {
        return new ResponseEntity<>(cartItemService.create(cartItemRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

