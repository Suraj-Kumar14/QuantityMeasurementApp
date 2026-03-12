package com.apps.quantitymeasurement.unit;

public interface IMeasurable {

//	String getMeasurementType();
	
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
