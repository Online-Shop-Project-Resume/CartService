package com.maksym.cartservice.service;


import com.maksym.cartservice.exception.EntityNotFoundException;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.repository.CartItemRepository;
import com.maksym.cartservice.staticObject.StaticCartItem;
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

class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartService cartService;
    @InjectMocks
    private CartItemService cartItemService;
    private final CartItem cartItem = StaticCartItem.cartItem1();
    private final CartItem cartItem2 = StaticCartItem.cartItem2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(cartService.getById(StaticCart.ID)).thenReturn(StaticCart.cart1());
	    when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem createdCartItem = cartItemService.create(cartItem);

        assertNotNull(createdCartItem);
        assertEquals(cartItem, createdCartItem);
        verify(cartService, times(1)).getById(StaticCart.ID);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testCreate_EntityNotFoundException_CartNotFound() {
        when(cartService.getById(StaticCart.ID)).thenThrow(new EntityNotFoundException("Cart not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cartItemService.create(cartItem));

        assertNotNull(exception);
        assertEquals("Cart not found", exception.getMessage());
        verify(cartService, times(1)).getById(StaticCart.ID);
        verifyNoInteractions(cartItemRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(cartService.getById(StaticCart.ID)).thenReturn(StaticCart.cart1());
        when(cartItemRepository.findById(StaticCartItem.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartItemService.getById(StaticCartItem.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartItemRepository, times(1)).findById(StaticCartItem.ID);
    }

    @Test
    void testGetAll() {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        cartItemList.add(cartItem2);
        Page<CartItem> cartItemPage = new PageImpl<>(cartItemList);
        Pageable pageable = Pageable.unpaged();
        when(cartItemRepository.findAll(pageable)).thenReturn(cartItemPage);

        Page<CartItem> result = cartItemService.getAll(pageable);

        assertEquals(cartItemList.size(), result.getSize());
        assertEquals(cartItem, result.getContent().get(0));
        assertEquals(cartItem2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(cartItemRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> cartItemService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartItemRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    CartItem existingCartItem = StaticCartItem.cartItem1();
        CartItem updatedCartItem = StaticCartItem.cartItem2();
        when(cartService.getById(StaticCart.ID)).thenReturn(StaticCart.cart1());
	    when(cartItemRepository.findById(StaticCartItem.ID)).thenReturn(java.util.Optional.of(existingCartItem));
        when(cartItemRepository.save(updatedCartItem)).thenReturn(updatedCartItem);

        CartItem result = cartItemService.updateById(StaticCartItem.ID, updatedCartItem);

        assertEquals(updatedCartItem, result);
        verify(cartItemRepository, times(1)).findById(StaticCartItem.ID);
        verify(cartItemRepository, times(1)).save(updatedCartItem);
    }

    @Test
    void testUpdateById_EntityNotFoundException_CartNotFound() {
        when(cartItemRepository.findById(StaticCartItem.ID)).thenReturn(java.util.Optional.of(cartItem));
        when(cartService.getById(StaticCart.ID)).thenThrow(new EntityNotFoundException("Cart not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cartItemService.updateById(StaticCartItem.ID, cartItem));

        assertNotNull(exception);
        assertEquals("Cart not found", exception.getMessage());
        verify(cartService, times(1)).getById(StaticCart.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        CartItem updatedCartItem = StaticCartItem.cartItem1();
        when(cartItemRepository.findById(StaticCartItem.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartItemService.updateById(StaticCartItem.ID, updatedCartItem));
        verify(cartItemRepository, times(1)).findById(StaticCartItem.ID);
        verify(cartItemRepository, never()).save(updatedCartItem);
    }

    @Test
    void testUpdateById_AnyException() {
        CartItem existingCartItem = StaticCartItem.cartItem1();
        CartItem updatedCartItem = StaticCartItem.cartItem2();
        when(cartItemRepository.findById(StaticCartItem.ID)).thenReturn(java.util.Optional.of(existingCartItem));
        when(cartService.getById(StaticCart.ID)).thenReturn(StaticCart.cart1());
	    when(cartItemRepository.save(updatedCartItem)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartItemService.updateById(StaticCartItem.ID, updatedCartItem));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartItemRepository, times(1)).save(updatedCartItem);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = cartItemService.deleteById(StaticCartItem.ID);

        verify(cartItemRepository).deleteById(StaticCartItem.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartItemRepository).deleteById(StaticCartItem.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartItemService.deleteById(StaticCartItem.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(cartItemRepository, times(1)).deleteById(StaticCartItem.ID);
    }
}