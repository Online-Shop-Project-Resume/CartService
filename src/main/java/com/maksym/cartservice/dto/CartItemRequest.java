package com.maksym.cartservice.dto;

import com.maksym.cartservice.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemRequest {
    private Integer quantity;
    private Long productId;
    private Cart cart;
}
