package com.workshop;

import com.dev10.exception.GenericRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {

    public List<Licence> cart = new ArrayList<>();

    /**
     * Ajoute une licence au panier
     *
     * @param licence
     */
    public void addValueToCart(Licence licence) {
        if (licence == null || licence.getLicenceType() == null) {
            throw new GenericRuntimeException("licence and licenceType can't be empty");
        }
        if (cart.stream().anyMatch(l -> l.getLicenceType().equals(licence.getLicenceType()))) {
            throw new GenericRuntimeException("licence of type " + licence.getLicenceType() + " already is in cart");
        }
        cart.add(licence);
    }

    /**
     * Retourne le montant total des licences du panier
     *
     * @return
     */
    public double getTotalCart() {
        // TODO: TDD
        return 0;
    }

}
