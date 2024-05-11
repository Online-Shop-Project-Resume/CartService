package com.maksym.cartservice.dto.mapper;

import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.dto.request.CartItemDtoRequest;
import com.maksym.cartservice.dto.response.CartItemDtoResponse;

public class CartItemDtoMapper {

    public static CartItem toModel(CartItemDtoRequest request) {
        CartItem model = new CartItem();

        model.setQuantity(request.getQuantity());
        model.setProductId(request.getProductId());
        Cart cart = new Cart();
        cart.setId(request.getCartId());
        model.setCart(cart);

        return model;
    }

    public static CartItemDtoResponse toResponse(CartItem model) {
        CartItemDtoResponse response = new CartItemDtoResponse();

        response.setId(model.getId());
        response.setQuantity(model.getQuantity());
        response.setProductId(model.getProductId());
        response.setCart(CartDtoMapper.toResponse(model.getCart()));

        return response;
    }

    private CartItemDtoMapper() {}

}
