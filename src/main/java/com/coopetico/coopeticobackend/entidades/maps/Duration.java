package com.coopetico.coopeticobackend.entidades.maps;

import java.io.Serializable;

/** The duration component for Directions API results. */
public class Duration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The numeric duration, in seconds. This is intended to be used only in algorithmic situations,
     * e.g. sorting results by some user specified metric.
     */
    public long inSeconds;

    /** The human-friendly duration. Use this for display purposes. */
    public String humanReadable;

    @Override
    public String toString() {
        return humanReadable;
    }
}