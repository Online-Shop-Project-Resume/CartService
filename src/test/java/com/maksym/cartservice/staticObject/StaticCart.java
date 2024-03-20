package com.maksym.cartservice.staticObject;

import com.maksym.cartservice.dto.CartRequest;
import com.maksym.cartservice.model.Cart;

public class StaticCart {
    public static Cart cart1(){
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserId("1");
        return cart;
    }

    public static Cart cart2(){
        Cart cart = new Cart();
        cart.setId(2L);
        cart.setUserId("2");
        return cart;
    }

    public static CartRequest cartRequest(){
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId("1");
        return cartRequest;
    }
}
