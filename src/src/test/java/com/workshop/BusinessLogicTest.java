package com.workshop;

import com.dev10.exception.GenericRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessLogicTest {

    private BusinessLogic businessLogic = new BusinessLogic();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        businessLogic.cart = new ArrayList<>();
    }

    @Test
    void onAddValueToCart_withEmptyCart_shouldAddTheValueToTheCart() {
        // GIVEN
        businessLogic.cart = new ArrayList<>();

        // WHEN
        businessLogic.addValueToCart("plop");

        // THEN
        assertEquals(1, businessLogic.cart.size());
        assertEquals("plop", businessLogic.cart.get(0));
    }

    @Test
    void onAddValueToCart_withAlreadyExistingValue_shouldThrowRunTimeException() {
        // GIVEN
        businessLogic.cart = new ArrayList<>();
        businessLogic.addValueToCart("plop");

        // WHEN-THEN
        GenericRuntimeException exception = assertThrows(
                GenericRuntimeException.class,
                () -> businessLogic.addValueToCart("plop")
        );
        assertEquals("value already is in cart", exception.getMessage());
    }

    @Test
    void onAddValueToCart_withValueEmpty_shouldThrowRunTimeException() {
        // TODO
    }
}