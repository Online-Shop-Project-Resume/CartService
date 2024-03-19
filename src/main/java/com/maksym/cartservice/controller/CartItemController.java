package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart-item")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAll();
    }

    @GetMapping("/{id}")
    public CartItem getCartItemById(@PathVariable Long id) {
        return cartItemService.getById(id);
    }

    @PostMapping
    public CartItem createCartItem(@RequestBody CartItemRequest cartItemRequest) {
        return cartItemService.create(cartItemRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteById(id);
    }
}

