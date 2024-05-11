package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.mapper.CartItemDtoMapper;
import com.maksym.cartservice.dto.request.CartItemDtoRequest;
import com.maksym.cartservice.dto.response.CartItemDtoResponse;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    @Operation(summary = "Create an cartItem", description = "Create new cartItem")
    @ApiResponse(responseCode = "201", description = "CartItem saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    @ApiResponse(responseCode = "503", description = "Database connection failed")
    public ResponseEntity<CartItemDtoResponse> createCartItem(@Valid @RequestBody CartItemDtoRequest cartItemDtoRequest) {
        CartItem cartItem = CartItemDtoMapper.toModel(cartItemDtoRequest);
        cartItem = cartItemService.create(cartItem);
        return new ResponseEntity<>(CartItemDtoMapper.toResponse(cartItem), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get CartItem", description = "Get CartItem By Id")
    @ApiResponse(responseCode = "200", description = "CartItem Get successfully")
    @ApiResponse(responseCode = "404", description = "CartItem with such an Id not found")
    public ResponseEntity<CartItemDtoResponse> getCartItemById(@PathVariable("id") Long id) {
        CartItem cartItem = cartItemService.getById(id);
        return new ResponseEntity<>(CartItemDtoMapper.toResponse(cartItem), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All CartItem", description = "Get All CartItem")
    @ApiResponse(responseCode = "200", description = "CartItem Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with CartItem have been found")
    public ResponseEntity<Page<CartItemDtoResponse>> getAllCartItem(Pageable pageable) {
        Page<CartItem> cartItemPage = cartItemService.getAll(pageable);
        return new ResponseEntity<>(cartItemPage.map(CartItemDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an cartItem", description = "Update an cartItem by Id and new CartItem")
    @ApiResponse(responseCode = "201", description = "CartItem updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "CartItem with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<CartItemDtoResponse> updateCartItem(@PathVariable("id") Long id, @Valid @RequestBody CartItemDtoRequest cartItemDtoRequest) {
        CartItem cartItem = CartItemDtoMapper.toModel(cartItemDtoRequest);
        cartItem = cartItemService.updateById(id, cartItem);
        return new ResponseEntity<>(CartItemDtoMapper.toResponse(cartItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an cartItem", description = "Delete an cartItem by id")
    @ApiResponse(responseCode = "204", description = "CartItem deleted successfully")
    public ResponseEntity<Boolean> deleteCartItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartItemService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}