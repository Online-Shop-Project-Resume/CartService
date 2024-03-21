package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.dtoMapper.CartItemMapper;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService{
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> getAll() {
        log.info("CartItem get all");
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getById(Long id) {
        log.info("CartItem by id: {}", id);
        return cartItemRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cart with id: "+id+" doesn't exist"));
    }

    @Override
    public CartItem create(CartItemRequest cartItemRequest) {
        log.info("CartItem create: {}", cartItemRequest);
        CartItem cartItem = CartItemMapper.toModel(cartItemRequest);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteById(Long id) {
        log.info("CartItem delete by id: {}", id);
        cartItemRepository.deleteById(id);
    }

    @Override
    public void deleteByCartId(Long cart_id) {
        log.info("CartItem delete all by cartId: {}", cart_id);
        cartItemRepository.deleteAllByCart_Id(cart_id);
    }
}
