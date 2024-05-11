package com.maksym.cartservice.service;

import com.maksym.cartservice.exception.EntityNotFoundException;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart create(Cart cart) {
        log.info("Cart create: {}", cart);

        return cartRepository.save(cart);
    }

    public Cart getById(Long id) {
        log.info("Cart get by id: {}", id);
        return cartRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cart with id: " + id + " does not exist"));
    }

    public Page<Cart> getAll(Pageable pageable) {
        log.info("Cart get all: {}", pageable);
        return cartRepository.findAll(pageable);
    }

    public Cart updateById(Long id, Cart cart) {
        getById(id);
        cart.setId(id);

        log.info("Cart update by id: {}", cart);
        return cartRepository.save(cart);
    }

    public Boolean deleteById(Long id) {
        log.info("Cart delete by id: {}", id);
        cartRepository.deleteById(id);
        return true;
    }
}
