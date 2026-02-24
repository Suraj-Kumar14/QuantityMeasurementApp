package com.apps.quantitymeasurement;

public enum WeightUnit implements IMeasurable{

	//Conversion factor to the base unit (grams)
	MILLIGRAM(0.001),
	GRAM(1.0),
	KILOGRAM(1000.0),
	POUND(453.592),
	TONNE(1_000_000.0);
	
	//Conversion factor to the base unit (grams)
	private final double conversionFactor;
	
	//Constructor to initialize the conversion factor
	WeightUnit(double conversionFactor){
		this.conversionFactor=conversionFactor;
	}
	
	public double getConversionFactor() {
		return conversionFactor;
	}
	
	public double convertToBaseUnit(double value) {
		return (value*getConversionFactor())*100.0/100.0;
	}
	
	public double convertFromBaseUnit(double baseValue) {
		return (baseValue/this.getConversionFactor()*100.0)/100.0;
	}
	
	public String getUnitName() {
		return this.name();
	}
	
}
