/*
  * @version 4.0
  * @author Suraj Kumar
 */
package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
		if(quantity1==null || quantity2==null || quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) {
			throw new IllegalArgumentException("Invalid quantity!");
		}
		return quantity1.equals(quantity2);
	}
	
	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U>quantity, U targetUnit){
		if(quantity==null || targetUnit==null) {
			throw new IllegalArgumentException("Invalid Input!");
		}
		
		return new Quantity<>(quantity.convertTo(targetUnit),targetUnit);
	}
		
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2){
		
		if(quantity1==null || quantity2==null || quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) {
			throw new IllegalArgumentException("Invalid quantity!");
		}
		
		return quantity1.add(quantity2);
	}
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit){
		if(quantity1==null || quantity2==null || quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) {
			throw new IllegalArgumentException("Invalid quantity");
		}
		
		if(targetUnit==null) {
			throw new IllegalArgumentException("Invalid targetUnit");
		}
		
		return quantity1.add(quantity2, targetUnit);
	}			
	
	
	public static void main(String[] args) {
		// Demonstration equality between the two quantities
		Quantity<WeightUnit> weightInGrams = new Quantity<>( 1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms = new Quantity<>( 1.0, WeightUnit. KILOGRAM);
		boolean areEqual = demonstrateEquality(weightInGrams, weightInKilograms);
		System.out.println("Are weights equal? " + areEqual);

		// Demonstration conversion between the two quantities
		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInGrams,
		WeightUnit.KILOGRAM);
		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " +
		convertedWeight.getUnit());

		// Demonstration addition of two quantities and return the result in the unit
		// of the first quantity
		Quantity<WeightUnit> weightInPounds = new Quantity<>( 2.20462, WeightUnit.POUND);
		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPounds);
		System.out.println("Sum Weight: " + sumWeight.getValue() + " " +
		sumWeight.getUnit());

		// Demonstration addition of two quantities and return the result in a specified
		// target unit
		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms,
		weightInPounds,
		WeightUnit.GRAM);
		System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " +
		sumWeightInGrams.getUnit());
	}
	
}
