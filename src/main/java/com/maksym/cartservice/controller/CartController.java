package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.service.CartService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAll();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getById(id);
    }

    @PostMapping
    public Cart addCart(@RequestBody CartRequest cartRequest) {
        return cartService.add(cartRequest);
    }


    @DeleteMapping("/{id}")
    public void deleteCartById(@PathVariable Long id) {
        cartService.deleteById(id);
    }

    @PutMapping("/empty/{id}")
    public void emptyCartById(@PathVariable Long id) {
        cartService.emptyById(id);
    }
}
