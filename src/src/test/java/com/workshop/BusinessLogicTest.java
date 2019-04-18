package com.workshop;

import com.dev10.exception.GenericRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MockLicence;

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
        Licence licence = MockLicence.mock(LicenceType.STUDENT, 0);
        businessLogic.addValueToCart(licence);

        // THEN
        assertEquals(1, businessLogic.cart.size());
        assertEquals(LicenceType.STUDENT, businessLogic.cart.get(0).getLicenceType());
    }

    @Test
    void onAddValueToCart_withAlreadyExistingValue_shouldThrowRunTimeException() {
        // GIVEN
        businessLogic.cart = new ArrayList<>();
        businessLogic.addValueToCart(MockLicence.mock(LicenceType.STUDENT, 0));

        // WHEN-THEN
        GenericRuntimeException exception = assertThrows(
                GenericRuntimeException.class,
                () -> businessLogic.addValueToCart(MockLicence.mock(LicenceType.STUDENT, 0))
        );
        assertEquals("licence of type STUDENT already is in cart", exception.getMessage());
    }

    @Test
    void onGetTotalCart_withEmptyCart_shouldReturn0() {
        // TODO
    }

    @Test
    void onGetTotalCart_withCartFull_shouldReturnTotal() {
        // TODO
    }

    @Test
    void onAddValueToCart_withValueEmpty_shouldThrowRunTimeException() {
        // TODO
    }
}