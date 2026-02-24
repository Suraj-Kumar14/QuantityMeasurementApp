package com.apps.quantitymeasurement;

public class Weight {

	private double value;
	private WeightUnit unit;
	private static final double EPSILON = 0.01;
	
	public Weight(double value,WeightUnit unit) {
		if(unit==null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if(Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Invalid value");			
		}
		this.value=value;
		this.unit=unit;	
	}
	
	public double getValue() {
		return value;
	}
	
	public WeightUnit getUnit() {
		return unit;
	}
	
	//Convert the length value to the base unit(inches) and round off to two decimal places
	 double convertToBaseUnit() {
		return ((this.value*this.unit.getConversionFactor())*100.0)/100.0;
	}
	
	// Compare two Length objects for equality based on their values in the base unit
	public boolean compare(Weight weight) {
		return Math.abs(this.convertToBaseUnit() - weight.convertToBaseUnit()) < EPSILON;
	}

	@Override
	public boolean equals(Object o) {
		if(this==o) {
			return true;
		}
		
		if(o==null || this.getClass()!=o.getClass()) {
			return false;
		}
		
		return this.compare((Weight)o);
	}
	
	@Override
	public String toString() {
		return value+" "+unit;
	}
	
	//ConvertTo method
	public Weight convertTo(WeightUnit unit) {
		double converted=((this.value*this.unit.getConversionFactor())/unit.getConversionFactor());
		return new Weight(converted,unit);
	}
	
	//add method
	public Weight add(Weight thatWeight) {
		double weight = this.convertToBaseUnit() + thatWeight.convertToBaseUnit();
		double result = convertFromBaseToTargetUnit(weight,this.unit);
		return new Weight(result,unit);
	}

	public Weight add(Weight weight, WeightUnit targetUnit) {
		Weight l1 = addAndConvert(this,targetUnit);
		Weight l2 = addAndConvert(weight,targetUnit);
		return l1.add(l2);
	}

	private Weight addAndConvert(Weight weight, WeightUnit targetUnit) {
		  return weight.convertTo(targetUnit);
		
	}
	
	private double convertFromBaseToTargetUnit(double weight, WeightUnit unit) {
		return weight/unit.getConversionFactor();
	}
	
	public static void main(String[] args) {
		Weight we1 = new Weight(1.0, WeightUnit.KILOGRAM);
		Weight we2 = new Weight(1000.0, WeightUnit.GRAM);
		
		System.out.println("Equality Comaprisons");
		System.out.println(we1.equals(we1));
		System.out.println(we1.equals(we2));
		System.out.println(new Weight(2.0,WeightUnit.POUND).equals(new Weight(2.0,WeightUnit.POUND)));
		System.out.println(we1.equals(new Weight(2.20462,WeightUnit.POUND)));
		System.out.println(new Weight(500.0,WeightUnit.GRAM).equals(new Weight(0.5,WeightUnit.KILOGRAM)));
		System.out.println(new Weight(1.0,WeightUnit.POUND).equals(new Weight(453.592,WeightUnit.GRAM)));
		
		
		System.out.println("\nUnit Conversion");	
		System.out.println(we1.convertTo(WeightUnit.GRAM));
		System.out.println(new Weight(2.0,WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));
		System.out.println(new Weight(500.0,WeightUnit.GRAM).convertTo(WeightUnit.POUND));
		System.out.println(new Weight(0.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
		
		System.out.println("\nAddition Operations");
		System.out.println(new Weight(1.0,WeightUnit.KILOGRAM).add(new Weight(1000.0,WeightUnit.GRAM),WeightUnit.GRAM));
		System.out.println(new Weight(1.0,WeightUnit.POUND).add(new Weight(453.592,WeightUnit.GRAM),WeightUnit.POUND));
		System.out.println(new Weight(2.0,WeightUnit.KILOGRAM).add(new Weight(4.0,WeightUnit.POUND),WeightUnit.KILOGRAM));
		
		System.out.println("\nCategory Incompatibility");
		System.out.println(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Length(1.0,LengthUnit.FEET)));
		}
}
