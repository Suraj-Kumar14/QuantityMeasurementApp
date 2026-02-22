package com.apps.quantitymeasurement;

public class Length {
	
	//Instance variables
	private double value;
	private LengthUnit unit;
	
	// Enum to represent different length units and their conversion factors
	// with the base unit being inches. This means all the conversion factors
	// are defined in terms of inches.
	public enum LengthUnit {
		FEET( 12.0),
		INCHES( 1.0);
	
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
	
	//Convert the length value to the base unit(inches)
	private double convertToBaseUnit() {
		return this.value*this.unit.getConversionFactor();
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

	// Main method for standalone testing
	
	public static void main(String[] args) {
	Length length1 = new Length( 1.0, LengthUnit. FEET);
	Length length2 = new Length( 12.0, LengthUnit. INCHES);
	System. out.println("Are lengths equal? " + length1.equals(length2)); // Should print true

	}
}
