package com.maksym.cartservice.dtoMapper;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;

public class CartMapper {
    public static Cart toModel(CartRequest cartRequest){
        Cart cart = new Cart();
        cart.setUserId(cartRequest.getUserId());
        return cart;
    }
}
