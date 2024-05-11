package com.maksym.cartservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksym.cartservice.dto.request.CartItemDtoRequest;
import com.maksym.cartservice.dto.response.CartItemDtoResponse;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.exception.EntityNotFoundException;
import com.maksym.cartservice.service.CartItemService;
import com.maksym.cartservice.staticObject.StaticCartItem;
import com.maksym.cartservice.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CartItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartItemService cartItemService;

    private final String DOCUMENTATION_URI = "http://swagger_documentation";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CartItemDtoRequest cartItemRequest = StaticCartItem.cartItemDtoRequest1();
    private final CartItem cartItemModel = StaticCartItem.cartItem1(); 
    private final CartItemDtoResponse cartItemResponse = StaticCartItem.cartItemDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CartItemController cartItemController = new CartItemController(cartItemService);
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController)
                .setControllerAdvice(new GlobalExceptionHandler(DOCUMENTATION_URI))
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(cartItemService.create(any(CartItem.class))).thenReturn(cartItemModel);

        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cartItemResponse.getId()))
                .andExpect(jsonPath("$.quantity").value(cartItemResponse.getQuantity()))
                .andExpect(jsonPath("$.productId").value(cartItemResponse.getProductId()))
                .andExpect(jsonPath("$.cart.id").value(cartItemResponse.getCart().getId()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(cartItemService.create(any(CartItem.class))).thenThrow(new EntityNotFoundException("CartItem not found"));

        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("CartItem not found"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartItemService).create(any(CartItem.class));

        mockMvc.perform(post("/api/cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(cartItemService.getById(StaticCartItem.ID)).thenReturn(cartItemModel);

        mockMvc.perform(get("/api/cart-item/{id}", StaticCartItem.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cartItemResponse.getId()))
                .andExpect(jsonPath("$.quantity").value(cartItemResponse.getQuantity()))
                .andExpect(jsonPath("$.productId").value(cartItemResponse.getProductId()))
                .andExpect(jsonPath("$.cart.id").value(cartItemResponse.getCart().getId()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(cartItemService.getById(any())).thenThrow(new EntityNotFoundException("CartItem not found"));

        mockMvc.perform(get("/api/cart-item/"+StaticCartItem.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("CartItem not found"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartItemService).getById(any());

        mockMvc.perform(get("/api/cart-item/"+StaticCartItem.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<CartItem> cartItemList = Arrays.asList(cartItemModel, StaticCartItem.cartItem1());
        Page<CartItem> cartItemPage = new PageImpl<>(cartItemList);
        Pageable pageable = Pageable.unpaged();
        when(cartItemService.getAll(pageable)).thenReturn(cartItemPage);

        mockMvc.perform(get("/api/cart-item/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(cartItemResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticCartItem.cartItemDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartItemService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/cart-item/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(cartItemService.updateById(any(), any(CartItem.class))).thenReturn(cartItemModel);

        mockMvc.perform(put("/api/cart-item/"+StaticCartItem.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cartItemResponse.getId()))
                .andExpect(jsonPath("$.quantity").value(cartItemResponse.getQuantity()))
                .andExpect(jsonPath("$.productId").value(cartItemResponse.getProductId()))
                .andExpect(jsonPath("$.cart.id").value(cartItemResponse.getCart().getId()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/cart-item/"+StaticCartItem.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(cartItemService.updateById(any(), any(CartItem.class))).thenThrow(new EntityNotFoundException("CartItem not found"));

        mockMvc.perform(put("/api/cart-item/"+StaticCartItem.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("CartItem not found"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartItemService).updateById(any(), any(CartItem.class));

        mockMvc.perform(put("/api/cart-item/"+StaticCartItem.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(cartItemService.deleteById(StaticCartItem.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/cart-item/"+StaticCartItem.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(cartItemService).deleteById(StaticCartItem.ID);

        mockMvc.perform(delete("/api/cart-item/"+StaticCartItem.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.documentationUri").value(DOCUMENTATION_URI));
    }
}