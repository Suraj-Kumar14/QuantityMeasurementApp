package com.apps.quantitymeasurement;

@FunctionalInterface
interface SupportsArithmetic{
    boolean isSupported();
}

public interface IMeasurable {

    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();

    double getConversionFactor();

    // Default lambda → all units support arithmetic
    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    // Validate arithmetic support
    default void validateOperationSupport(String operation) {
    }
}
