package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.dtoMapper.CartItemMapper;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarItemServiceImpl implements CartItemService{
    private final CartItemRepository cartItemRepository;

    public CarItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cart with id: "+id+" doesn't exist"));
    }

    @Override
    public CartItem create(CartItemRequest cartItemRequest) {
        CartItem cartItem = CartItemMapper.toModel(cartItemRequest);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
