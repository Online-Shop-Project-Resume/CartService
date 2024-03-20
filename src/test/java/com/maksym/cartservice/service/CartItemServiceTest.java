package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.repository.CartItemRepository;
import com.maksym.cartservice.repository.CartItemRepositoryTest;
import com.maksym.cartservice.staticObject.StaticCartItem;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CartItemServiceTest {

    private final long id = 1L;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(StaticCartItem.cartItem1());
        cartItems.add(StaticCartItem.cartItem2());

        when(cartItemRepository.findAll()).thenReturn(cartItems);

        List<CartItem> result = cartItemService.getAll();

        assertEquals(2, result.size());
        assertEquals(StaticCartItem.cartItem1(), result.get(0));
        assertEquals(StaticCartItem.cartItem2(), result.get(1));
    }

    @Test
    void testGetById() {
        CartItem cartItem = StaticCartItem.cartItem1();

        when(cartItemRepository.findById(id)).thenReturn(Optional.of(cartItem));

        CartItem result = cartItemService.getById(id);

        assertEquals(cartItem, result);
    }

    @Test
    void testGetById_EntityNotFoundException() {
        when(cartItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartItemService.getById(id));
    }

    @Test
    void testCreate() {
        CartItemRequest cartItemRequest = StaticCartItem.cartItemRequest1();
        CartItem cartItem = StaticCartItem.cartItem1();

        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem result = cartItemService.create(cartItemRequest);

        assertEquals(cartItem, result);
    }

    @Test
    void testDeleteById() {
        cartItemService.deleteById(id);

        verify(cartItemRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteByCartId() {
        cartItemService.deleteByCartId(id);

        verify(cartItemRepository, times(1)).deleteAllByCart_Id(id);
    }
}

