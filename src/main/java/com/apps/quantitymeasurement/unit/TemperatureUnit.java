package com.apps.quantitymeasurement.unit;

import com.apps.quantitymeasurement.exception.QuantityMeasurementException;

public enum TemperatureUnit implements IMeasurable{
	CELSIUS,
	FAHRENHEIT,
	KELVIN;	
	
	
//	@Override
//	public String getMeasurementType() {
//		return "TEMPERATURE";
//	}

	@Override
	public double convertToBaseUnit(double value) {
		switch(this) {
			case CELSIUS:
				return value;
			
			case FAHRENHEIT:
				return (value-32)*5/9.0;
				
			case KELVIN:
				return value - 273.15;
				
			default: 
				throw new QuantityMeasurementException("Unsupported temperature unit");
		}
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		switch(this) {
		case CELSIUS:
			return baseValue;
		
		case FAHRENHEIT:
			return (baseValue*9/5.0)+32;
			
		case KELVIN:
			return baseValue + 273.15;
			
		default: 
			throw new QuantityMeasurementException("Unsupported temperature unit");
		}
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public boolean supportsArithmetic() {
		return false;
	}
  
	@Override
	public void validateOperationSupport(String operation) {
		if(operation.equals("ADD") || operation.equals("DIVIDE") || operation.equals("SUBTRACT")) {
			String message = this.name()+" does not support "+operation+" operations!";
			throw new UnsupportedOperationException(message);
	    }
	}

	@Override
	public double getConversionFactor() {
		return 1.0;
	}
	
	@Override
	public String getMeasurableType() {
		return this.getClass().getSimpleName();
	}
}