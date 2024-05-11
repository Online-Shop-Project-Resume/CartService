package com.maksym.cartservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDtoRequest {

    @Positive(message = "Quantity must be a positive number")
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    @Positive(message = "Product Id must be a positive number")
    @NotNull(message = "Product Id cannot be null")
    private Long productId;

    @Positive(message = "Cart must be a positive number")
    @NotNull(message = "Cart cannot be null")
    private Long cartId;
}
