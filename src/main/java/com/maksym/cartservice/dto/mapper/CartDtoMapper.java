package com.maksym.cartservice.dto.mapper;

import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.dto.request.CartDtoRequest;
import com.maksym.cartservice.dto.response.CartDtoResponse;

public class CartDtoMapper {

    public static Cart toModel(CartDtoRequest request) {
        Cart model = new Cart();

        model.setUserId(request.getUserId());

        return model;
    }

    public static CartDtoResponse toResponse(Cart model) {
        CartDtoResponse response = new CartDtoResponse();

        response.setId(model.getId());
        response.setUserId(model.getUserId());

        return response;
    }

    private CartDtoMapper() {}

}
