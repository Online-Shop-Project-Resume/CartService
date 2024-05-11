package com.maksym.cartservice.service;

import com.maksym.cartservice.exception.EntityNotFoundException;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.repository.CartItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;

    public CartItemService(CartService cartService, CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem create(CartItem cartItem) {
        log.info("CartItem create: {}", cartItem);
        cartItem.setCart(cartService.getById(cartItem.getCart().getId()));
        return cartItemRepository.save(cartItem);
    }

    public CartItem getById(Long id) {
        log.info("CartItem get by id: {}", id);
        return cartItemRepository.findById(id).orElseThrow(()->new EntityNotFoundException("CartItem with id: " + id + " does not exist"));
    }

    public Page<CartItem> getAll(Pageable pageable) {
        log.info("CartItem get all: {}", pageable);
        return cartItemRepository.findAll(pageable);
    }

    public CartItem updateById(Long id, CartItem cartItem) {
        getById(id);
        cartItem.setId(id);
        cartItem.setCart(cartService.getById(cartItem.getCart().getId()));
        log.info("CartItem update by id: {}", cartItem);
        return cartItemRepository.save(cartItem);
    }

    public Boolean deleteById(Long id) {
        log.info("CartItem delete by id: {}", id);
        cartItemRepository.deleteById(id);
        return true;
    }
}
