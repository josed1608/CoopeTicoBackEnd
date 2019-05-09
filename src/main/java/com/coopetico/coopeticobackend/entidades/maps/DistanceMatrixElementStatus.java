package com.coopetico.coopeticobackend.entidades.maps;

/**
 * The status result for a single {@link com.google.maps.model.DistanceMatrixElement}.
 *
 * @see <a
 *     href="https://developers.google.com/maps/documentation/distance-matrix/intro#StatusCodes">
 *     Documentation on status codes</a>
 */
public enum DistanceMatrixElementStatus {
    /** Indicates that the response contains a valid result. */
    OK,

    /** Indicates that the origin and/or destination of this pairing could not be geocoded. */
    NOT_FOUND,

    /** Indicates that no route could be found between the origin and destination. */
    ZERO_RESULTS
}