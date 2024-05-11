package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.mapper.CartDtoMapper;
import com.maksym.cartservice.dto.request.CartDtoRequest;
import com.maksym.cartservice.dto.response.CartDtoResponse;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @Operation(summary = "Create an cart", description = "Create new cart")
    @ApiResponse(responseCode = "201", description = "Cart saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    @ApiResponse(responseCode = "503", description = "Database connection failed")
    public ResponseEntity<CartDtoResponse> createCart(@Valid @RequestBody CartDtoRequest cartDtoRequest) {
        Cart cart = CartDtoMapper.toModel(cartDtoRequest);
        cart = cartService.create(cart);
        return new ResponseEntity<>(CartDtoMapper.toResponse(cart), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Cart", description = "Get Cart By Id")
    @ApiResponse(responseCode = "200", description = "Cart Get successfully")
    @ApiResponse(responseCode = "404", description = "Cart with such an Id not found")
    public ResponseEntity<CartDtoResponse> getCartById(@PathVariable("id") Long id) {
        Cart cart = cartService.getById(id);
        return new ResponseEntity<>(CartDtoMapper.toResponse(cart), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Cart", description = "Get All Cart")
    @ApiResponse(responseCode = "200", description = "Cart Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Cart have been found")
    public ResponseEntity<Page<CartDtoResponse>> getAllCart(Pageable pageable) {
        Page<Cart> cartPage = cartService.getAll(pageable);
        return new ResponseEntity<>(cartPage.map(CartDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an cart", description = "Update an cart by Id and new Cart")
    @ApiResponse(responseCode = "201", description = "Cart updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Cart with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<CartDtoResponse> updateCart(@PathVariable("id") Long id, @Valid @RequestBody CartDtoRequest cartDtoRequest) {
        Cart cart = CartDtoMapper.toModel(cartDtoRequest);
        cart = cartService.updateById(id, cart);
        return new ResponseEntity<>(CartDtoMapper.toResponse(cart), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an cart", description = "Delete an cart by id")
    @ApiResponse(responseCode = "204", description = "Cart deleted successfully")
    public ResponseEntity<Boolean> deleteCart(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}