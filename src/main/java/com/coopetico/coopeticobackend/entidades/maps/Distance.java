package com.coopetico.coopeticobackend.entidades.maps;

import java.io.Serializable;

/** The distance component for Directions API results. */
public class Distance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The numeric distance, in meters. This is intended to be used only in algorithmic situations,
     * e.g. sorting results by some user specified metric.
     */
    public long inMeters;

    /**
     * The human-friendly distance. This is rounded and in an appropriate unit for the request. The
     * units can be overridden with a request parameter.
     */
    public String humanReadable;

    @Override
    public String toString() {
        return humanReadable;
    }
}
