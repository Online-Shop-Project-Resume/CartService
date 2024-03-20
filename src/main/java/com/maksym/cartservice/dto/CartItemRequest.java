package com.maksym.cartservice.dto;

import com.maksym.cartservice.model.Cart;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemRequest {
    @Positive
    private Integer quantity;
    @NotNull
    private Long productId;
    private Cart cart;
}
