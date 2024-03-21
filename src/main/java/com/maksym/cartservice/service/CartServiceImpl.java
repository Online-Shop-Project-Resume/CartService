package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.dtoMapper.CartMapper;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartItemServiceImpl cartItemService;

    public CartServiceImpl(CartRepository cartRepository, CartItemServiceImpl cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    @Override
    public List<Cart> getAll() {
        log.info("Cart get all");
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(Long id) {
        log.info("Cart get by id: {}", id);
        return cartRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cart with id: "+id+" doesn't exist"));
    }

    @Override
    public Cart add(CartRequest cartRequest) {
        log.info("Cart create: {}", cartRequest);
        Cart cart = CartMapper.toModel(cartRequest);
        return cartRepository.save(cart);
    }

    @Override
    public void emptyById(Long cartId) {
        log.info("Cart empty by id: {}", cartId);
        cartItemService.deleteByCartId(cartId);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Cart delete by id: {}", id);
        cartRepository.deleteById(id);
    }
}
