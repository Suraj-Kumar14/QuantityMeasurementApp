
package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.unit.*;

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
	
   public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2){
		
		if(quantity1==null || quantity2==null || quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) {
			throw new IllegalArgumentException("Invalid quantity!");
		}
		
		return quantity1.subtract(quantity2);
	}
	
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit){
		if(quantity1==null || quantity2==null || quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) {
			throw new IllegalArgumentException("Invalid quantity");
		}
		
		if(targetUnit==null) {
			throw new IllegalArgumentException("Invalid targetUnit");
		}
		
		return quantity1.subtract(quantity2, targetUnit);
	}
	
	public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> quantity1, Quantity<U> quantity2) {
		if(quantity1==null || quantity2==null || quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) {
			throw new IllegalArgumentException("Invalid quantity!");
		}
		
		return quantity1.divide(quantity2);
	}
	
	private static QuantityMeasurementApp instance;

	public QuantityMeasurementController controller;

	public IQuantityMeasurementRepository repository;

	private QuantityMeasurementApp() {
		this.repository = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);
		this.controller = new QuantityMeasurementController(service);
	}
	
	public static QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}
	
//	public static void main(String[] args) {
//		// Demonstration equality between the two quantities
//		Quantity<WeightUnit> weightInGrams = new Quantity<>( 1000.0, WeightUnit.GRAM);
//		Quantity<WeightUnit> weightInKilograms = new Quantity<>( 1.0, WeightUnit. KILOGRAM);
//		boolean areEqual = demonstrateEquality(weightInGrams, weightInKilograms);
//		System.out.println("Are weights equal? " + areEqual);
//
//		// Demonstration conversion between the two quantities
//		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInGrams,
//		WeightUnit.KILOGRAM);
//		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " +
//		convertedWeight.getUnit());
//
//		// Demonstration addition of two quantities and return the result in the unit
//		// of the first quantity
//		Quantity<WeightUnit> weightInPounds = new Quantity<>( 2.20462, WeightUnit.POUND);
//		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPounds);
//		System.out.println("Sum Weight: " + sumWeight.getValue() + " " +
//		sumWeight.getUnit());
//
//		// Demonstration addition of two quantities and return the result in a specified
//		// target unit
//		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms,
//		weightInPounds,
//		WeightUnit.GRAM);
//		System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " +
//		sumWeightInGrams.getUnit());
//		
//		    System.out.println("Subtraction of Feet and Inches : "+demonstrateSubtraction(new Quantity<LengthUnit>(10.0,LengthUnit.FEET),new Quantity<LengthUnit>(6.0,LengthUnit.INCHES)));
//	        
//	        System.out.println("Subtraction of Kg and Gram : "+demonstrateSubtraction(new Quantity<WeightUnit>(10.0,WeightUnit.KILOGRAM), new Quantity<WeightUnit>(5000.0, WeightUnit.GRAM)));
//	        
//	        System.out.println("Subtraction of Litre and mililitre : "+demonstrateSubtraction(new Quantity<VolumeUnit>(5.0, VolumeUnit.LITRE), new Quantity<VolumeUnit>(500.0,VolumeUnit.MILLILITRE)));
//	        
//	        System.out.println("Subtraction of feet with inche to inche : "+demonstrateSubtraction(new Quantity<LengthUnit>(10.0, LengthUnit.FEET),new Quantity<LengthUnit>(6.0,LengthUnit.INCHES),LengthUnit.INCHES));
//	        
//	        System.out.println("Subtraction of kg with gram to gram : "+demonstrateSubtraction(new Quantity<WeightUnit>(10.0,WeightUnit.KILOGRAM),new Quantity<WeightUnit>(5000.0,WeightUnit.GRAM),WeightUnit.GRAM));
//	        
//	        System.out.println("Division of Feet with feet : "+demonstrateDivision(new Quantity<LengthUnit>(10.0, LengthUnit.FEET), new Quantity<LengthUnit>(2.0, LengthUnit.FEET)));
//	       
//	        System.out.println("Division of inche with feet : "+demonstrateDivision(new Quantity<LengthUnit>(24.0,LengthUnit.INCHES),new Quantity<LengthUnit>(2.0,LengthUnit.FEET)));
	
    public static void main(String[] args) {
        QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();

        QuantityDTO threeFeet = new QuantityDTO(3, "FEET", "LENGTH");
        QuantityDTO thirtySixInches = new QuantityDTO(36, "INCHES", "LENGTH");
        QuantityDTO twoYards = new QuantityDTO(2, "YARDS", "LENGTH");
        QuantityDTO targetInches = new QuantityDTO(0, "INCHES", "LENGTH");

        QuantityDTO oneLitre = new QuantityDTO(1, "LITRE", "VOLUME");
        QuantityDTO thousandMilliLitres = new QuantityDTO(1000, "MILLILITRE", "VOLUME");
        QuantityDTO targetLitre = new QuantityDTO(0, "LITRE", "VOLUME");

        QuantityDTO hundredCelsius = new QuantityDTO(100, "CELSIUS", "TEMPERATURE");
        QuantityDTO twoTwelveFahrenheit = new QuantityDTO(212, "FAHRENHEIT", "TEMPERATURE");

        System.out.println("===== UC15 Quantity Measurement Application =====");

        System.out.println("\n1. Comparison");
        System.out.println(app.controller.performComparison(threeFeet, thirtySixInches));

        System.out.println("\n2. Conversion");
        System.out.println(app.controller.performConversion(threeFeet, thirtySixInches));

        System.out.println("\n3. Addition with same unit as first quantity");
        System.out.println(app.controller.performAddition(oneLitre, thousandMilliLitres));

        System.out.println("\n4. Addition with target unit");
        System.out.println(app.controller.performAddition(oneLitre, thousandMilliLitres, targetLitre));

        System.out.println("\n5. Subtraction with same unit as first quantity");
        System.out.println(app.controller.performSubtraction(twoYards, threeFeet));

        System.out.println("\n6. Subtraction with target unit");
        System.out.println(app.controller.performSubtraction(twoYards, threeFeet, targetInches));

        System.out.println("\n7. Division");
        System.out.println(app.controller.performDivision(thirtySixInches, threeFeet));

        System.out.println("\n8. Temperature conversion");
        System.out.println(app.controller.performConversion(hundredCelsius, twoTwelveFahrenheit));

        System.out.println("\n9. Repository history printed if your repository supports it.");
    }
}
	

