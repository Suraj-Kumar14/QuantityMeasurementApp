package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
	
	private double value;
	private U unit;
	
	public Quantity(double value,U unit) {
		if(Double.isNaN(value)||Double.isInfinite(value)) {
			throw new IllegalArgumentException("Invalid Value!");
		}
		
		if(unit==null) {
			throw new IllegalArgumentException("Invalid unit!");
		}
		
		this.value=value;
		this.unit=unit;
	}
	
	public double getValue() {
		return value;
	}
	
	public U getUnit() {
		return unit;
	}
	
	//convertTo method
	public <U extends IMeasurable>double convertTo(U targetUnit) {
		//convert to base unit
		double baseUnit=this.unit.convertToBaseUnit(value);
		//convert to target unit
		double targetUnits=targetUnit.convertFromBaseUnit(baseUnit);
		return targetUnits;
	}
	
	//add method
	public Quantity<U>add(Quantity<U>other){
		double sum1=this.convertTo(unit);
		double sum2=other.convertTo(unit);
		return new Quantity<>((sum1+sum2),unit);
	}
	
	//add with targetUnit
	public Quantity<U>add(Quantity<U>other,U targetUnit){
		double sum1=this.convertTo(targetUnit);
		double sum2=other.convertTo(targetUnit);
		return new Quantity<>((sum1+sum2),targetUnit);
	}
	
	//equals
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof Quantity<?>)) return false;

	    Quantity<?> other = (Quantity<?>) obj;

	    
	    if (!this.unit.getClass().equals(other.unit.getClass())) {
	        return false;
	    }

	    double baseValue1 = this.unit.convertToBaseUnit(this.value);
	    double baseValue2 = other.unit.convertToBaseUnit(other.value);

	    return Math.abs(baseValue1 - baseValue2) < 0.0001;
	}	
	
	public Quantity<U> subtract(Quantity<U> other){
		if(other==null ) {
			throw new IllegalArgumentException("Invalid input!");
		}
		double sub1= this.convertTo(unit);
		double sub2 = other.convertTo(unit);
		return new Quantity<>((sub1-sub2),unit);
	}
	     
	public Quantity<U> subtract(Quantity<U> other, U targetUnit){
		if(other==null || targetUnit==null) {
			throw new IllegalArgumentException("Invalid input!");
		}
		double sub1 = this.convertTo(targetUnit);
		double sub2 = other.convertTo(targetUnit);
		return new Quantity<>((sub1-sub2),targetUnit);
	} 
	
	public double divide(Quantity<U>other) {
		if(other==null ) {
			throw new IllegalArgumentException("Invalid input!");
		}
		
		double baseValue1 = this.unit.convertToBaseUnit(this.value);
	    double baseValue2 = other.unit.convertToBaseUnit(other.value);
	    
	    if (Math.abs(baseValue2) < 1e-10) {
	        throw new ArithmeticException("Division by zero");
	    }
	    
	    return baseValue1 / baseValue2;
	}
	
	@Override
	public String toString() {
		return value+" "+unit;
	}
	
	public static void main(String args[]) {
		Quantity<LengthUnit>lengthInFeet = new Quantity<>(10.0,LengthUnit.FEET);
		Quantity<LengthUnit> lengthInInches = new Quantity<>(120.0,LengthUnit.INCHES);
		boolean isEqual = lengthInFeet.equals(lengthInInches);
		System.out.println("Are lengths equal? + "+isEqual);
		
		Quantity<WeightUnit>weightInKilograms=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		Quantity<WeightUnit>weightInGrams=new Quantity<>(1000.0,WeightUnit.GRAM);
		boolean isEqual1=weightInKilograms.equals(weightInGrams);
		System.out.println("Are weights equal? + "+isEqual);
		
		double convertedLength = lengthInFeet.convertTo(LengthUnit.INCHES);
		System.out.println("10 feet in inches: "+convertedLength);
		
		Quantity<LengthUnit>totalLength = lengthInFeet.add(lengthInInches,LengthUnit.FEET);
		System.out.println("Total Length in feet: "+totalLength.getValue()+" "+totalLength.getUnit());
		
		Quantity<WeightUnit>weightInPounds = new Quantity<>(2.0,WeightUnit.POUND);
		
		Quantity<WeightUnit> totalWeight = weightInKilograms.add(weightInPounds,WeightUnit.KILOGRAM);
		
		System.out.println("Total Weight in Kilograms: "+ totalWeight.getValue()+" "+totalWeight.getUnit());
	
	
		Quantity<VolumeUnit>v1=new Quantity<>(1.0,VolumeUnit.LITRE);
		Quantity<VolumeUnit>v2=new Quantity<>(1000.0,VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit>v3=new Quantity<>(1.0,VolumeUnit.GALLON);
	
		System.out.println(v1.equals(v2));
		System.out.println(v1.equals(v3));
		System.out.println(v3.equals(v1));
		
		System.out.println(v1.convertTo(VolumeUnit.MILLILITRE));
		System.out.println(v3.convertTo(VolumeUnit.LITRE));
		System.out.println(v2.convertTo(VolumeUnit.GALLON));
	}
}