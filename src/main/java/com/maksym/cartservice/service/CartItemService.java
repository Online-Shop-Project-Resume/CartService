package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.model.CartItem;

import java.util.List;

public interface CartItemService {
    public List<CartItem> getAll();
    public CartItem getById(Long id);
    public CartItem create(CartItemRequest cartItemRequest);
    public void deleteById(Long id);

    void deleteByCartId(Long id);
}
