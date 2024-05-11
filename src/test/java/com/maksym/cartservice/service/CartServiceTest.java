package com.maksym.cartservice.service;


import com.maksym.cartservice.exception.EntityNotFoundException;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.repository.CartRepository;
import com.maksym.cartservice.staticObject.StaticCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartService cartService;
    private final Cart cart = StaticCart.cart1();
    private final Cart cart2 = StaticCart.cart2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
	    when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartService.create(cart);

        assertNotNull(createdCart);
        assertEquals(cart, createdCart);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testCreate_DataAccessException() {
        when(cartRepository.findById(StaticCart.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.getById(StaticCart.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartRepository, times(1)).findById(StaticCart.ID);
    }

    @Test
    void testGetAll() {
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        cartList.add(cart2);
        Page<Cart> cartPage = new PageImpl<>(cartList);
        Pageable pageable = Pageable.unpaged();
        when(cartRepository.findAll(pageable)).thenReturn(cartPage);

        Page<Cart> result = cartService.getAll(pageable);

        assertEquals(cartList.size(), result.getSize());
        assertEquals(cart, result.getContent().get(0));
        assertEquals(cart2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(cartRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> cartService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Cart existingCart = StaticCart.cart1();
        Cart updatedCart = StaticCart.cart2();
	    when(cartRepository.findById(StaticCart.ID)).thenReturn(java.util.Optional.of(existingCart));
        when(cartRepository.save(updatedCart)).thenReturn(updatedCart);

        Cart result = cartService.updateById(StaticCart.ID, updatedCart);

        assertEquals(updatedCart, result);
        verify(cartRepository, times(1)).findById(StaticCart.ID);
        verify(cartRepository, times(1)).save(updatedCart);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Cart updatedCart = StaticCart.cart1();
        when(cartRepository.findById(StaticCart.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartService.updateById(StaticCart.ID, updatedCart));
        verify(cartRepository, times(1)).findById(StaticCart.ID);
        verify(cartRepository, never()).save(updatedCart);
    }

    @Test
    void testUpdateById_AnyException() {
        Cart existingCart = StaticCart.cart1();
        Cart updatedCart = StaticCart.cart2();
        when(cartRepository.findById(StaticCart.ID)).thenReturn(java.util.Optional.of(existingCart));
	    when(cartRepository.save(updatedCart)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.updateById(StaticCart.ID, updatedCart));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartRepository, times(1)).save(updatedCart);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = cartService.deleteById(StaticCart.ID);

        verify(cartRepository).deleteById(StaticCart.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartRepository).deleteById(StaticCart.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.deleteById(StaticCart.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartRepository, times(1)).deleteById(StaticCart.ID);
    }
}