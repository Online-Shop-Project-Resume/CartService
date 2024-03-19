package com.maksym.cartservice.dtoMapper;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;
import com.maksym.cartservice.model.CartItem;

public class CartItemMapper {
    public static CartItem toModel(CartItemRequest cartItemRequest){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cartItemRequest.getCart());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setProductId(cartItemRequest.getProductId());
        return cartItem;
    }
}
