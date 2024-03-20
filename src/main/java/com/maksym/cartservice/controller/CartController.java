package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.service.CartService;
import com.maksym.cartservice.service.CartServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        return new ResponseEntity<>(cartService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cart> addCart(@RequestBody @Valid CartRequest cartRequest) {
        return new ResponseEntity<>(cartService.add(cartRequest), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartById(@PathVariable Long id) {
        cartService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/empty/{id}")
    public ResponseEntity emptyCartById(@PathVariable Long id) {
        cartService.emptyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
