package com.maksym.cartservice.repository;

import com.maksym.cartservice.model.CartItem;
import com.maksym.cartservice.staticObject.StaticCartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartItemRepositoryTest {

    private final long id = 1L;
    @Autowired
    private CartItemRepository cartItemRepository;
//    TODO
//    @Test
//    public void testDeleteAllByCart_Id() {
//        // Given
//        CartItem cartItem1 = StaticCartItem.cartItem1();
//        CartItem cartItem2 = StaticCartItem.cartItem2();
//
//        cartItemRepository.save(cartItem1);
//        cartItemRepository.save(cartItem2);
//
//        // When
//        cartItemRepository.deleteAllByCart_Id(id);
//
//        // Then
//        List<CartItem> remainingCartItems = cartItemRepository.findAll();
//        assertThat(remainingCartItems).hasSize(1);
//        assertThat(remainingCartItems.get(0).getId()).isEqualTo(2L);
//    }
}
