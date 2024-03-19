package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAll();
    Cart getById(Long id);
    Cart add(CartRequest cartRequest);
    Cart emptyById(Long id);
    void deleteById(Long id);
}
