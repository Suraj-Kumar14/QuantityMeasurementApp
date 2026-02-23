package com.apps.quantitymeasurement;

public class Length {
	
	//Instance variables
	private double value;
	private LengthUnit unit;
	private static final double EPSILON = 0.01;
	
	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		if(unit==null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if(Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Invalid value");			
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
	
	public Length convertTo(LengthUnit targetUnit) {
	    double result = value * unit.getConversionFactor()/ targetUnit.getConversionFactor();
	    return new Length(result, targetUnit);
	}
	
	public Length add(Length thatLength) {
		double lengthInInches = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
		double result = convertFromBaseToTargetUnit(lengthInInches,this.unit);
		return new Length(result,unit);
	}
	
	public Length add(Length length, LengthUnit targetUnit) {
		Length l1 = addAndConvert(this,targetUnit);
		Length l2 = addAndConvert(length,targetUnit);
		return l1.add(l2);
	}

	private Length addAndConvert(Length length, LengthUnit targetUnit) {
		  return length.convertTo(targetUnit);
		
	}
	
	private double convertFromBaseToTargetUnit(double lengthInInches, LengthUnit targetUnit) {
		return lengthInInches/targetUnit.getConversionFactor();
	}
	
	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return value + " " + unit ;
	}
	
	// Main method for standalone testing
	public static void main(String[] args) {
/*		
        Length len1 = new Length(1,Length.LengthUnit.FEET);
		Length len2 = new Length(12,Length.LengthUnit.INCHES);
		System.out.println("Are lengths equals? " + len1.equals(len2)); // Should print true;
		
		Length len3 = new Length(1.0,Length.LengthUnit.YARDS);
		Length len4 = new Length(36.0,Length.LengthUnit.INCHES);
		System.out.println("Are lengths equals? " +len3.equals(len4));
		
		Length len5 = new Length(100.0,Length.LengthUnit.CENTIMETERS);
		Length len6 = new Length(39.37,Length.LengthUnit.INCHES);
		System.out.println("Are lengths equals? " +len5.equals(len6));
		
*/		
		Length len1 = new Length(1.0,LengthUnit.FEET);
		Length len2 = new Length(1.0,LengthUnit.YARDS);
		
//		System.out.println("Convert 1 FEET to INCHES = "+len1.convertTo(LengthUnit.INCHES));
//		System.out.println("Add 1 FEET and 12 INCHES = "+len1.add(new Length(12.0,LengthUnit.INCHES)));
//
//		System.out.println("Check 36.0 INCHES equals 1.0 YARDS = "+new Length(36.0,LengthUnit.INCHES).equals(len2));
//		System.out.println("Add 1.0 YARDS and 3.0 FEET = "+len2.add(new Length(3.0,LengthUnit.FEET),LengthUnit.YARDS));
//		
//		System.out.println("Convert 2.54 CENTIMETERS into INCHES = "+new Length(2.54,LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES));
//		System.out.println("Add 5.0 FEET and 0.0 INCHES ouput in FEET = "+new Length(5.0,LengthUnit.FEET).add(new Length(0.0,LengthUnit.INCHES),LengthUnit.FEET));
//	
//		System.out.println("LengthUnit.FEET.convertToBaseUnit(12.0) = "+LengthUnit.FEET.convertToBaseUnit(12));
//		System.out.println("LengthUnit.INCHES.convertToBaseUnit(12.0) = "+LengthUnit.INCHES.convertFromBaseUnit(12.0));
	}
}
