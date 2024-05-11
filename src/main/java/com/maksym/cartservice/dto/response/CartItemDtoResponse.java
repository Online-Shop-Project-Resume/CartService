package com.maksym.cartservice.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDtoResponse {

    private Long id;

    private Integer quantity;

    private Long productId;

    private CartDtoResponse cart;
}
