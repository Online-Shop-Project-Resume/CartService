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
    private final ProductService productService;

    public CartItemController(CartItemServiceImpl cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
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
        //Check if product exists
        Boolean existsProduct = productService.existsProduct(cartItemRequest.getProductId()).getBody();
        if(existsProduct==null || !existsProduct)
            throw new EntityNotFoundException("Product with id: " + cartItemRequest.getProductId() + " doesn't exist");

        return new ResponseEntity<>(cartItemService.create(cartItemRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

