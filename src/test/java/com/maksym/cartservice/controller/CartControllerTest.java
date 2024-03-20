package com.maksym.cartservice.controller;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.exception.GlobalExceptionHandler;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.service.CartService;
import com.maksym.cartservice.service.CartServiceImpl;
import com.maksym.cartservice.staticObject.StaticCart;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartControllerTest {

    private final long id = 1L;
    private MockMvc mockMvc;

    @Mock
    private CartServiceImpl cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAllCarts() throws Exception {
        List<Cart> carts = new ArrayList<>();
        carts.add(StaticCart.cart1());
        carts.add(StaticCart.cart2());

        when(cartService.getAll()).thenReturn(carts);

        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andDo(print());
    }

    @Test
    void testGetCartById() throws Exception {
        Cart cart = StaticCart.cart1();

        when(cartService.getById(id)).thenReturn(cart);

        mockMvc.perform(get("/api/cart/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void testAddCart() throws Exception {
        CartRequest cartRequest = StaticCart.cartRequest();
        Cart cart = StaticCart.cart1();

        when(cartService.add(cartRequest)).thenReturn(cart);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void testDeleteCartById() throws Exception {
        mockMvc.perform(delete("/api/cart/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(cartService, times(1)).deleteById(id);
    }

    @Test
    void testEmptyCartById() throws Exception {
        long id = 1L;

        mockMvc.perform(put("/api/cart/empty/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(cartService, times(1)).emptyById(id);
    }

    @Test
    void testHandleConstraintViolationException() throws Exception {
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testHandlerNotFoundException() throws Exception {
        when(cartService.getById(id)).thenThrow(new EntityNotFoundException("Cart with id: " + id + " not found"));

        mockMvc.perform(get("/api/cart/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(jsonPath("$").value("Cart with id: 1 not found"))
                .andDo(print());
    }
}

