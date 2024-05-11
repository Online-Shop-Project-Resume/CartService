package com.maksym.cartservice.staticObject;

import com.maksym.cartservice.dto.request.CartDtoRequest;
import com.maksym.cartservice.dto.response.CartDtoResponse;
import com.maksym.cartservice.model.Cart;


public class StaticCart {

    public static final Long ID = 1L;

    public static Cart cart1() {
        Cart model = new Cart();
        model.setId(ID);
        model.setUserId("userId");
        return model;
    }

    public static Cart cart2() {
        Cart model = new Cart();
        model.setId(ID);
        model.setUserId("userId");
        return model;
    }

    public static CartDtoRequest cartDtoRequest1() {
        CartDtoRequest dtoRequest = new CartDtoRequest();
        dtoRequest.setUserId("userId");
        return dtoRequest;
    }

    public static CartDtoResponse cartDtoResponse1() {
        CartDtoResponse dtoResponse = new CartDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setUserId("userId");
        return dtoResponse;
    }

    public static CartDtoResponse cartDtoResponse2() {
        CartDtoResponse dtoResponse = new CartDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setUserId("userId");
        return dtoResponse;
    }
}
