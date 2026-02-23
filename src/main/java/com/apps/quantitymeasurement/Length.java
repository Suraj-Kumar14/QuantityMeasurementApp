package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {
	
	//Instance variables
	private double value;
	private LengthUnit unit;
	private static final double EPSILON = 0.01;

	
	// Enum to represent different length units and their conversion factors
	// with the base unit being inches. This means all the conversion factors
	// are defined in terms of inches.
	public enum LengthUnit {
		FEET( 12.0),
		INCHES( 1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
	
		private final double conversionFactor;
	
		LengthUnit(double conversionFactor) {
		this. conversionFactor = conversionFactor;
		}
	
		public double getConversionFactor() {
			return conversionFactor;
		}	
	}
	
	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		if(unit==null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if(Double.isNaN(value)) {
			throw new IllegalArgumentException("Unit cannot be null");			
		}
		this.value=value;
		this.unit=unit;
	}
	
	//Convert the length value to the base unit(inches) and round off to two decimal places
	private double convertToBaseUnit() {
		return ((this.value*this.unit.getConversionFactor())*100.0)/100.0;

	}
	
	// Compare two Length objects for equality based on their values in the base unit
	public boolean compare(Length len) {
		return Math.abs(this.convertToBaseUnit() - len.convertToBaseUnit()) < EPSILON;
	}
	

	// Equals method is overridden to firstly check if the two objects are the same reference.
	// If not, it checks if the other object is null or of a different class.
	// Finally, calls the compare method to determine equality based on converted values.
	@Override
	public boolean equals (Object o) {
		if(this==o) {
			return true;
		}
		
		if(null==o) {
			return false;
		}
		
		if(this.getClass()!=o.getClass()) {
			return false;
		}
		
		return this.compare((Length)o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(convertToBaseUnit());
	}
	
	public Length convertTo(LengthUnit targetUnit) {
	    double result = this.value * this.unit.getConversionFactor()/ targetUnit.getConversionFactor();
	    return new Length(result, targetUnit);
	}
	
	public Length add(Length thatLength) {
		double lengthInInches = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
		double result = convertFromBaseToTargetUnit(lengthInInches,this.unit);
		return new Length(result,unit);
	}

	private double convertFromBaseToTargetUnit(double lengthInInches, LengthUnit targetUnit) {
		return lengthInInches/targetUnit.getConversionFactor();
	}
	
	@Override
	public String toString() {
		return value + " " + unit ;
	}
	
	// Main method for standalone testing
	public static void main(String[] args) {
		Length len1 = new Length(1,Length.LengthUnit.FEET);
		Length len2 = new Length(12,Length.LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + len1.equals(len2)); // Should print true;
		
		Length len3 = new Length(1.0,Length.LengthUnit.YARDS);
		Length len4 = new Length(36.0,Length.LengthUnit.INCHES);
		System.out.println("Are lengths equals? " +len3.equals(len4));
		
		Length len5 = new Length(100.0,Length.LengthUnit.CENTIMETERS);
		Length len6 = new Length(39.37,Length.LengthUnit.INCHES);
		System.out.println("Are lengths equals? " +len5.equals(len6));
	}
}
