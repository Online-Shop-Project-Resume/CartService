package com.maksym.cartservice.staticObject;

import com.maksym.cartservice.dto.CartItemRequest;
import com.maksym.cartservice.model.CartItem;

public class StaticCartItem {
    public static CartItem cartItem1(){
        CartItem cartItem = new CartItem();
        cartItem.setProductId(1L);
        cartItem.setCart(StaticCart.cart1());
        cartItem.setQuantity(10);
        cartItem.setId(1L);
        return cartItem;
    }

    public static CartItem cartItem2(){
        CartItem cartItem = new CartItem();
        cartItem.setProductId(2L);
        cartItem.setCart(StaticCart.cart1());
        cartItem.setQuantity(20);
        cartItem.setId(2L);
        return cartItem;
    }
    public static CartItemRequest cartItemRequest1(){
        CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setCart(StaticCart.cart1());
        cartItemRequest.setQuantity(10);
        cartItemRequest.setProductId(1L);
        return cartItemRequest;
    }
}
