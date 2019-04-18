package com.workshop;

import com.dev10.exception.GenericRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {

    public List<String> cart = new ArrayList<>();

    public void addValueToCart(String value) {
        if (StringUtils.isBlank(value)) {
            throw new GenericRuntimeException("value can't be empty");
        }
        if (cart.contains(value)) {
            throw new GenericRuntimeException("value already is in cart");
        }
        cart.add(value);
    }

}
