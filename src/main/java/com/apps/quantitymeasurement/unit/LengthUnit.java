package com.apps.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable{
	
	FEET(1.0),
	INCHES(1.0/12.0),
	YARDS(3.0),
	CENTIMETERS(1.0/30.48),
	METERS(3.28084);
	
	private final double conversionFactor;
	
	LengthUnit(double conversionFactor){
		this.conversionFactor=conversionFactor;
	}
	
//	@Override
//	public double getConversionFactor() {
//		return conversionFactor;
//	}
	
	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public double convertToBaseUnit(double value) {
		return (value*conversionFactor)*100.0/100.0;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return (baseValue/conversionFactor)*100.0/100.0;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public String getMeasurableType() {
		return this.getClass().getSimpleName();
	}
	
}
