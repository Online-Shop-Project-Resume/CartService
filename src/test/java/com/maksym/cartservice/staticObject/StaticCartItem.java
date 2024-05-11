package com.maksym.cartservice.staticObject;

import com.maksym.cartservice.dto.request.CartItemDtoRequest;
import com.maksym.cartservice.dto.response.CartItemDtoResponse;
import com.maksym.cartservice.model.CartItem;


public class StaticCartItem {

    public static final Long ID = 1L;

    public static CartItem cartItem1() {
        CartItem model = new CartItem();
        model.setId(ID);
        model.setQuantity(1);
        model.setProductId(1L);
        model.setCart(StaticCart.cart1());
        return model;
    }

    public static CartItem cartItem2() {
        CartItem model = new CartItem();
        model.setId(ID);
        model.setQuantity(2);
        model.setProductId(2L);
        model.setCart(StaticCart.cart2());
        return model;
    }

    public static CartItemDtoRequest cartItemDtoRequest1() {
        CartItemDtoRequest dtoRequest = new CartItemDtoRequest();
        dtoRequest.setQuantity(1);
        dtoRequest.setProductId(1L);
        dtoRequest.setCartId(1L);
        return dtoRequest;
    }

    public static CartItemDtoResponse cartItemDtoResponse1() {
        CartItemDtoResponse dtoResponse = new CartItemDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setQuantity(1);
        dtoResponse.setProductId(1L);
        dtoResponse.setCart(StaticCart.cartDtoResponse1());
        return dtoResponse;
    }

    public static CartItemDtoResponse cartItemDtoResponse2() {
        CartItemDtoResponse dtoResponse = new CartItemDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setQuantity(2);
        dtoResponse.setProductId(2L);
        dtoResponse.setCart(StaticCart.cartDtoResponse1());
        return dtoResponse;
    }
}
