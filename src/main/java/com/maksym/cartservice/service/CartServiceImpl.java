package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.dtoMapper.CartMapper;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cart with id: "+id+" doesn't exist"));
    }

    @Override
    public Cart add(CartRequest cartRequest) {
        Cart cart = CartMapper.toModel(cartRequest);
        return cartRepository.save(cart);
    }

    @Override
    public Cart emptyById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}