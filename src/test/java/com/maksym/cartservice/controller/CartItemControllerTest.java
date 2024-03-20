package com.maksym.cartservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.exception.GlobalExceptionHandler;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.service.CartItemServiceImpl;
import com.maksym.cartservice.staticObject.StaticCartItem;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CartItemControllerTest {

    private final long id = 1L;
    private MockMvc mockMvc;

    @Mock
    private CartItemServiceImpl cartItemService;

    @InjectMocks
    private CartItemController cartItemController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    void testGetAllCartItems() throws Exception {
        // Mock service method
        when(cartItemService.getAll()).thenReturn(Collections.singletonList(StaticCartItem.cartItem1()));

        mockMvc.perform(get("/api/cart-item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testGetCartItemById() throws Exception {
        CartItem item = StaticCartItem.cartItem1();

        // Mock service method
        when(cartItemService.getById(id)).thenReturn(item);

        mockMvc.perform(get("/api/cart-item/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testCreateCartItem() throws Exception {
        CartItemRequest request = StaticCartItem.cartItemRequest1();
        CartItem createdItem = StaticCartItem.cartItem1();

        // Mock service method
        when(cartItemService.create(any())).thenReturn(createdItem);

        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdItem.getId()));
    }

    @Test
    void testDeleteCartItemById() throws Exception {

        mockMvc.perform(delete("/api/cart-item/{id}", id))
                .andExpect(status().isNoContent());

        // Verify that the service method was called
        verify(cartItemService, times(1)).deleteById(id);
    }

    @Test
    void testCreateCartItem_ValidationException() throws Exception {
        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateCartItem_EntityNotFoundException() throws Exception {
        // Simulate entity not found exception in the service layer
        doThrow(new EntityNotFoundException("Item not found")).when(cartItemService).create(any());

        CartItemRequest request = StaticCartItem.cartItemRequest1();

        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
