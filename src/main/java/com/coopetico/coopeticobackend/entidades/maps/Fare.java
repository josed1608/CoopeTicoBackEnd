package com.coopetico.coopeticobackend.entidades.maps;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * A representation of ticket cost for use on public transit.
 *
 * <p>See the <a href="https://developers.google.com/maps/documentation/directions/intro#Routes">
 * Routes Documentation</a> for more detail.
 */
public class Fare implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The currency that the amount is expressed in. */
    public Currency currency;

    /** The total fare amount, in the currency specified in {@link #currency}. */
    public BigDecimal value;

    @Override
    public String toString() {
        return String.format("%s %s", value, currency);
    }
}
