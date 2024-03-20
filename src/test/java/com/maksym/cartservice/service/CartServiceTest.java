package com.maksym.cartservice.service;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.repository.CartRepository;
import com.maksym.cartservice.staticObject.StaticCart;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    private final long id = 1L;
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Cart> expectedCarts = new ArrayList<>();
        expectedCarts.add(StaticCart.cart1());

        when(cartRepository.findAll()).thenReturn(expectedCarts);

        List<Cart> actualCarts = cartService.getAll();

        assertEquals(expectedCarts.size(), actualCarts.size());
        assertEquals(expectedCarts.get(0).getId(), actualCarts.get(0).getId());
        assertEquals(expectedCarts.get(0).getUserId(), actualCarts.get(0).getUserId());
    }

    @Test
    void testGetById_ExistingId() {
        Cart expectedCart = StaticCart.cart1();

        when(cartRepository.findById(id)).thenReturn(Optional.of(expectedCart));

        Cart actualCart = cartService.getById(id);

        assertEquals(expectedCart.getId(), actualCart.getId());
        assertEquals(expectedCart.getUserId(), actualCart.getUserId());
    }

    @Test
    void testGetById_NonExistingId() {
        when(cartRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartService.getById(id));
    }

    @Test
    void testAdd() {
        CartRequest cartRequest = StaticCart.cartRequest();
        Cart expectedCart = StaticCart.cart1();

        when(cartRepository.save(any())).thenReturn(expectedCart);

        Cart actualCart = cartService.add(cartRequest);

        assertEquals(expectedCart.getId(), actualCart.getId());
        assertEquals(expectedCart.getUserId(), actualCart.getUserId());
    }

    @Test
    void testDeleteById_ExistingId() {
        when(cartRepository.existsById(id)).thenReturn(true);

        cartService.deleteById(id);

        verify(cartRepository, times(1)).deleteById(id);
    }
}

