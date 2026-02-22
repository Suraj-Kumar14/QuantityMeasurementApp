package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {
	
	//Instance variables
	private double value;
	private LengthUnit unit;
	
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
		this.value=value;
		this.unit=unit;
	}
	
	//Convert the length value to the base unit(inches) and round off to two decimal places
	private double convertToBaseUnit() {
		double convertedValue= this.value*this.unit.getConversionFactor();
		return Math.round(convertedValue*100)/100;
	}
	
	// Compare two Length objects for equality based on their values in the base unit
	public boolean compare(Length len) {
		return Double.compare(this.convertToBaseUnit(),len.convertToBaseUnit())==0;
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
	
	// Main method for standalone testing
	
	public static void main(String[] args) {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + length1.equals(length2)); // Should print true;
		
		Length length3 = new Length(1, LengthUnit.YARDS);
		Length length4 = new Length(36, LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + length3.equals(length4)); // Should print true;
		
		Length length5 = new Length(100, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + length5.equals(length6)); // Should print true;
	}
}
