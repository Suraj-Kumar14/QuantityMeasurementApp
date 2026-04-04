package com.app.microservices.quantity_service.unit;

public interface IMeasurable {

	double getConversionFactor();	
	double convertToBaseUnit(double value);
	double convertFromBaseUnit(double baseValue);
	String getUnitName();
	String getMeasurableType();
	
	default boolean supportsArithmetic() {
		return true;
	}
	
	default void validateOperationSupport(String operation) {}
	
}