package com.coopetico.coopeticobackend.entidades.maps;

import java.io.Serializable;

/**
 * Represents a single row in a Distance Matrix API response. A row represents the results for a
 * single origin.
 */
public class DistanceMatrixRow implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The results for this row, or individual origin. */
    public DistanceMatrixElement[] elements;

    @Override
    public String toString() {
        return String.format("[DistanceMatrixRow %d elements]", elements.length);
    }
}
