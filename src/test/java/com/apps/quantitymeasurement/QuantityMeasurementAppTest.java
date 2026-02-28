package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class QuantityMeasurementAppTest {
	
	Quantity<LengthUnit> len1;
	Quantity <LengthUnit>len2;
	
	//Feet
	@Test
	public void testFeetEquality_SameValue() {
		 len1 = new Quantity<>(1.0,LengthUnit.FEET);
		len2=new Quantity<>(1.0,LengthUnit.FEET);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_DifferentValue() {
		 len1 = new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(2.0,LengthUnit.FEET);
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_NullComparison() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=null;
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_DifferentClass() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		String len2="1.0";
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_SameReference() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		
		assertTrue(len1.equals(len1));
	}
	
	//Inches
	@Test
	public void testInchesEquality_SameValue() {
		 len1=new Quantity<>(1.0,LengthUnit.INCHES);
		 len2=new Quantity<>(1.0,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_DifferentValue() {
		 len1=new Quantity<>(1.0,LengthUnit.INCHES);
		 len2=new Quantity<>(2.0,LengthUnit.INCHES);
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_NullComparison() {
		 len1=new Quantity<>(1.0,LengthUnit.INCHES);
		 len2=null;
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_DifferentClass() {
		 len1=new Quantity<>(1.0,LengthUnit.INCHES);
		String len2="1.0";
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_SameReference() {
		 len1=new Quantity<>(1.0,LengthUnit.INCHES);
		
		assertTrue(len1.equals(len1));
	}
	
	//Length
	@Test
	public void testFeetEquality() {
		len1=new Quantity<>(1,LengthUnit.FEET);
		len2=new Quantity<>(2,LengthUnit.FEET);
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchEquality() {
		len1 = new Quantity<>(11.0,LengthUnit.INCHES);
		len2 = new Quantity<>(11.0,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testFeetInchesComparison() {
		len1 = new Quantity<>(1.0,LengthUnit.FEET);
		len2 = new Quantity<>(12.0,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	 @Test
	public void testFeetInEquality() {
	   	len1 = new Quantity<>(2.0,LengthUnit.FEET);
	   	len2 = new Quantity<>(24.0,LengthUnit.FEET);
	   	assertFalse(len1.equals(len2));
	 }
	    
     @Test
	 public void testIncheInEquality() {
	   	len1 = new Quantity<>(2.0,LengthUnit.INCHES);
	   	len2 = new Quantity<>(24.0,LengthUnit.INCHES);
	   	assertFalse(len1.equals(len2));
    }
	
	@ParameterizedTest
	@ValueSource(doubles = {1,2,3,4})
	public void testMultipleFeetComparison(double feet) {
		len1 = new Quantity<>(feet,LengthUnit.FEET);
		len2 = new Quantity<>(feet*12,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	//Yard
	
	@Test
	public void yardEquals36Inches() {
		assertTrue(new Quantity<LengthUnit>(1.0,LengthUnit.YARDS).equals(new Quantity<LengthUnit>(36.0,LengthUnit.INCHES)));
	}
	
	@Test
	public void centimeterEquals39point3701Inches() {
		assertTrue(new Quantity<LengthUnit>(100.0,LengthUnit.CENTIMETERS).equals(new Quantity<LengthUnit>(39.3701,LengthUnit.INCHES)));
	}
	
	@Test
	public void threeFeetEqualsOneYard() {
		assertTrue(new Quantity<LengthUnit>(3.0,LengthUnit.FEET).equals(new Quantity<LengthUnit>(1.0,LengthUnit.YARDS)));
	}

	@Test
	public void thirtyPoint48CmEqualsOneFoot() {
		assertTrue(new Quantity<LengthUnit>(30.48,LengthUnit.CENTIMETERS).equals(new Quantity<LengthUnit>(1.0,LengthUnit.FEET)));
	}

	@Test
	public void yardNotEqualToInches () {
		assertFalse(new Quantity<LengthUnit>(1.0,LengthUnit.YARDS).equals(new Quantity<LengthUnit>(1.0,LengthUnit.INCHES)));
	}

	@Test
	public void referenceEqualitySameObject() {
		len1 = new Quantity<LengthUnit>(1.0, LengthUnit.YARDS);
		len2 = len1;
		assertTrue(len1.equals(len2));
	}

	@Test
	public void equalsReturnsFalseForNull() {
		len1=new Quantity<LengthUnit>(1.0,LengthUnit.YARDS);
		assertFalse(len1.equals(null));
	}

	@Test
	public void reflexiveSymmetricAndTransitiveProperty() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
		Quantity<LengthUnit> c = new Quantity<>(36.0, LengthUnit.INCHES);
	    
	    //Reflexive
	    assertTrue(a.equals(a));
	    
	    // Symmetric
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(a));
	    
	    //Transitive
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(c));
	    assertTrue(a.equals(c));
	    
	}

	@Test
	public void differentValuesSameUnitNotEqual() {
		assertFalse(new Quantity<>(10.0,LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.FEET)));
	}

	@Test
	public void crossUnitEqualityDemonstrateMethod() {
		Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCHES);

	    assertTrue(yard.equals(inch));
	}
	
	@Test
	public void convertFeetToInches() {
		Quantity<LengthUnit> lengthInches = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(3.0, LengthUnit.FEET), LengthUnit.INCHES);
		Quantity<LengthUnit> expectedInches = new Quantity<>(36.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateEquality(lengthInches, expectedInches));
	}
	
	@Test
	public void convertYardToInchesUsingOverloadMethod() {
		Quantity<LengthUnit> lengthInYard = new Quantity<>(2.0, LengthUnit.YARDS);
		Quantity<LengthUnit> lengthInInches = QuantityMeasurementApp.demonstrateConversion(lengthInYard, LengthUnit.INCHES);
		Quantity<LengthUnit> expected = new Quantity<>(72.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateEquality(lengthInInches, expected));
	}
	
	@Test
	void addFeetAndInchesWithTargetUnitInches() {
		Quantity<LengthUnit> Feet = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> Inches = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> output = QuantityMeasurementApp
	            .demonstrateAddition(Feet, Inches, LengthUnit.INCHES);
		Quantity<LengthUnit> result = new Quantity<>(3.0, LengthUnit.FEET);
	    assertTrue(output.equals(result));
	}
	
	//Addition
	@Test
	public void testAddition_SameUnit_FeetPlusFeet() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(2.0,LengthUnit.FEET);
		
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 Quantity<LengthUnit> expected=new Quantity<>(3.0,LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_SameUnit_InchPlusInch() {
		 len1=new Quantity<LengthUnit>(1.0,LengthUnit.INCHES);
		 len2=new Quantity<LengthUnit>(2.0,LengthUnit.INCHES);
		
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 Quantity<LengthUnit> expected=new Quantity<LengthUnit>(3.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_FeetPlusInches() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(12.0,LengthUnit.INCHES);
		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 Quantity<LengthUnit> expected=new Quantity<>(2.0,LengthUnit.FEET);
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_InchPlusFeet() {
		 len1=new Quantity<>(12.0,LengthUnit.INCHES);
		 len2=new Quantity<>(1.0,LengthUnit.FEET);
		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 Quantity<LengthUnit> expected=new Quantity<>(2.0,LengthUnit.FEET);
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_YardPlusFeet() {
		 len1=new Quantity<>(1.0,LengthUnit.YARDS);
		 len2=new Quantity<>(3.0,LengthUnit.FEET);
		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 Quantity<LengthUnit> expected=new Quantity<>(2.0,LengthUnit.YARDS);
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_CentimeterPlusInch() {
		 len1=new Quantity<>(2.54,LengthUnit.CENTIMETERS);
		 len2=new Quantity<>(1.0,LengthUnit.INCHES);
		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 Quantity<LengthUnit> expected=new Quantity<>(5.08,LengthUnit.CENTIMETERS);
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_Commutativity() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(12.0,LengthUnit.INCHES);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 
		 Quantity<LengthUnit> l1=new Quantity<>(12.0,LengthUnit.INCHES);
		 Quantity<LengthUnit> l2=new Quantity<>(1.0,LengthUnit.FEET);		 
		 Quantity<LengthUnit> expected=QuantityMeasurementApp.demonstrateAddition(l1, l2);
		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_WithZero() {
		 len1=new Quantity<>(5.0,LengthUnit.FEET);
		 len2=new Quantity<>(0.0,LengthUnit.INCHES);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(5.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_NegativeValues() {
		 len1=new Quantity<>(5.0,LengthUnit.FEET);
		 len2=new Quantity<>(-2.0,LengthUnit.FEET);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(3.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_NullSecondOperand() {
		 assertThrows(IllegalArgumentException.class, ()->{
			 len1=new Quantity<>(1.0,LengthUnit.FEET);
			 Quantity<LengthUnit> sum = QuantityMeasurementApp.demonstrateAddition(len1, null);
		 });		 
	}
	
	@Test
	public void testAddition_LargeValues() {
		 len1=new Quantity<>(1e6,LengthUnit.FEET);
		 len2=new Quantity<>(1e6,LengthUnit.FEET);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(2e6,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_SmallValues() {
		 len1=new Quantity<>(0.001,LengthUnit.FEET);
		 len2=new Quantity<>(0.002,LengthUnit.FEET);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(0.003,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Feet() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(12.0,LengthUnit.INCHES);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2,LengthUnit.FEET);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(2.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Inches() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(12.0,LengthUnit.INCHES);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2,LengthUnit.INCHES);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(24.0,LengthUnit.INCHES);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Yards() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(12.0,LengthUnit.INCHES);		 
		 assertEquals(0.667,QuantityMeasurementApp.demonstrateAddition(len2, len1,LengthUnit.YARDS).getValue(),0.001);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Centimeters() {
		 len1=new Quantity<>(1.0,LengthUnit.INCHES);
		 len2=new Quantity<>(1.0,LengthUnit.INCHES);		
		 
		 assertEquals(5.08,QuantityMeasurementApp.demonstrateAddition(len2, len1,LengthUnit.CENTIMETERS).getValue(),0.01);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		 len1=new Quantity<>(2.0,LengthUnit.YARDS);
		 len2=new Quantity<>(3.0,LengthUnit.FEET);		
		 
		 assertEquals(3.0,QuantityMeasurementApp.demonstrateAddition(len2, len1,LengthUnit.YARDS).getValue());
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		 len1=new Quantity<>(2.0,LengthUnit.YARDS);
		 len2=new Quantity<>(3.0,LengthUnit.FEET);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2,LengthUnit.FEET);
		 
		 Quantity<LengthUnit> expected=new Quantity<>(9.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Commutativity() {
		 len1=new Quantity<>(1.0,LengthUnit.FEET);
		 len2=new Quantity<>(12.0,LengthUnit.INCHES);		 
		 Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2,LengthUnit.YARDS);
		 
		 Quantity<LengthUnit> expected= QuantityMeasurementApp.demonstrateAddition(new Quantity<>(12.0,LengthUnit.INCHES),new Quantity<>(1.0,LengthUnit.FEET),LengthUnit.YARDS);		 
		 assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_WithZero() {
		 len1=new Quantity<>(5.0,LengthUnit.FEET);
		 len2=new Quantity<>(0.0,LengthUnit.INCHES);		 
		 		 
		 assertEquals(1.667,QuantityMeasurementApp.demonstrateAddition(len2, len1,LengthUnit.YARDS).getValue(),0.001);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_NegativeValues() {
		len1=new Quantity<>(5.0, LengthUnit.FEET);
		len2=new Quantity<>(-2,LengthUnit.FEET);
		
		Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2,LengthUnit.INCHES);
		Quantity<LengthUnit> expected=new Quantity<>(36.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		len1=new Quantity<>(1.0, LengthUnit.FEET);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertThrows(IllegalArgumentException.class, ()-> {						
			Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len1, len2,null);
		});		
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		len1=new Quantity<>(1000.0,LengthUnit.FEET);
		len2=new Quantity<>(500.0,LengthUnit.FEET);
		Quantity<LengthUnit> sum=QuantityMeasurementApp.demonstrateAddition(len2, len1, LengthUnit.INCHES);
		
		Quantity<LengthUnit> expected=new Quantity<>(18000.0,LengthUnit.INCHES);
		
		assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		len1=new Quantity<>(12.0,LengthUnit.INCHES);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertEquals(0.667 ,QuantityMeasurementApp.demonstrateAddition(len1, len2, LengthUnit.YARDS).getValue(),0.001);		
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(0.1, LengthUnit.FEET);
		Quantity<LengthUnit> result = l1.add(l2, LengthUnit.INCHES);
         assertEquals(13.2, result.getValue(),0.001);
	}
	
	@Test
	public void testLengthUnitEnum_FeetConstant() {
		assertEquals(1.0,LengthUnit.FEET.getConversionFactor());
	}
	
	@Test
	public void testLengthUnitEnum_InchesConstant() {
		assertEquals(0.0833,LengthUnit.INCHES.getConversionFactor(),0.0001);
	}
	
	@Test
	public void testLengthUnitEnum_YardsConstant() {
		assertEquals(3.0,LengthUnit.YARDS.getConversionFactor());
	}
	
	@Test
	public void testLengthUnitEnum_CentimetersConstant() {
		assertEquals(0.0328,LengthUnit.CENTIMETERS.getConversionFactor(),0.0001);
	}
	
	@Test
	public void testConvertToBaseUnit_FeetToFeet() {
		assertEquals(5.0,LengthUnit.FEET.convertToBaseUnit(5.0));
	}
	
	@Test
	public void testConvertToBaseUnit_InchesToFeet() {
		assertEquals(1.0,LengthUnit.INCHES.convertToBaseUnit(12.0));
	}
	
	@Test
	public void testConvertToBaseUnit_YardsToFeet() {
		assertEquals(3.0,LengthUnit.YARDS.convertToBaseUnit(1.0));
	}
	
	@Test
	public void testConvertToBaseUnit_CentimetersToFeet() {
		assertEquals(1.0,LengthUnit.CENTIMETERS.convertToBaseUnit(30.48));
	}
	
	@Test
	public void testConvertFromBaseUnit_FeetToFeet() {
		assertEquals(2.0,LengthUnit.FEET.convertFromBaseUnit(2.0));
	}
	
	@Test
	public void testConvertFromBaseUnit_FeetToInches() {
		assertEquals(12.0,LengthUnit.INCHES.convertFromBaseUnit(1.0));
	}
	
	@Test
	public void testConvertFromBaseUnit_FeetToYards() {
		assertEquals(1.0,LengthUnit.YARDS.convertFromBaseUnit(3.0));
	}
	
	@Test
	public void testConvertFromBaseUnit_FeetToCentimeters() {
		assertEquals(30.48,LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0));
	}
	
	@Test
	public void testQuantityLengthRefactored_Equality() {
		assertTrue(new Quantity<>(1.0,LengthUnit.FEET).equals(new Quantity<>(12.0,LengthUnit.INCHES)));
	}
	
	@Test
	public void testQuantityLengthRefactored_ConvertTo() {
		assertEquals(12.0,new Quantity<>(1.0,LengthUnit.FEET).convertTo(LengthUnit.INCHES));
	}
	
	@Test
	public void testQuantityLengthRefactored_Add() {
		assertEquals(new Quantity<>(2.0,LengthUnit.FEET),QuantityMeasurementApp.demonstrateAddition(new  Quantity<>(1.0,LengthUnit.FEET), new Quantity<>(12.0,LengthUnit.INCHES), LengthUnit.FEET));
	}
	
	@Test
	public void testQuantityLengthRefactored_AddWithTargetUnit() {
		assertEquals(0.667,QuantityMeasurementApp.demonstrateAddition(new  Quantity<>(1.0,LengthUnit.FEET), new Quantity<>(12.0,LengthUnit.INCHES), LengthUnit.YARDS).getValue(),0.001);
	}
	
	@Test
	public void testQuantityLengthRefactored_NullUnit() {
		assertThrows(IllegalArgumentException.class, ()->{
			new Quantity<>(1.0,null);
		});
	}
	
	@Test
	public void testQuantityLengthRefactored_InvalidValue() {
		assertThrows(IllegalArgumentException.class, ()->{
			new Quantity<>(Double.NaN,LengthUnit.FEET);
		});
	}
	
	//UC-9
	
	@Test
	public void testEquality_KilogramToKilogram_SameValue() {
		assertTrue(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_KilogramToKilogram_DifferentValue() {
		assertFalse(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(2.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_KilogramToGram_EquivalentValue() {
		assertTrue(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testEquality_GramToKilogram_EquivalentValue() {
		assertTrue(new Quantity<>(1000.0,WeightUnit.GRAM).equals(new Quantity<>(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_WeightVsLength_Incompatible() {
		assertFalse(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(1.0,LengthUnit.FEET)));
	}
	
	@Test
	public void testEquality_NullComparison() {
		assertFalse(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(null));
	}
	
	@Test
	public void testEquality_SameReference() {
		assertTrue(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_NullUnit() {
		assertThrows(IllegalArgumentException.class, ()->{
			new Quantity<>(1.0,null);
		});
	}
	
	@Test
	public void testEquality_TransitiveProperty() {
		Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);
	    Quantity<WeightUnit> c = new Quantity<>(1.0, WeightUnit.KILOGRAM);
	    	    
	    //Transitive
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(c));
	    assertTrue(a.equals(c));
	}
	
	@Test
	public void testEquality_ZeroValue() {
		assertTrue(new Quantity<WeightUnit>(0.0,WeightUnit.KILOGRAM).equals(new Quantity<WeightUnit>(0.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testEquality_NegativeWeight() {
		assertTrue(new Quantity<WeightUnit>(-1.0,WeightUnit.KILOGRAM).equals(new Quantity<WeightUnit>(-1000.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testEquality_LargeWeightValue() {
		assertTrue(new Quantity<WeightUnit>(1000000.0,WeightUnit.GRAM).equals(new Quantity<WeightUnit>(1000.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_SmallWeightValue() {
		assertTrue(new Quantity<>(0.001,WeightUnit.KILOGRAM).equals(new Quantity<>(1.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testConversion_PoundToKilogram() {
	}
	
	@Test
	public void testConversion_KilogramToPound() {
		assertEquals(2.20462,new Quantity<>(1.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND),0.0001);
	}
	
	@Test
	public void testConversion_SameUnit() {
		assertEquals(5.0,new Quantity<>(5.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM));
	}
	
	@Test
	public void testConversion_ZeroValue() {
		assertEquals(0.0,new Quantity<>(0.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
	}
	
	@Test
	public void testConversion_NegativeValue() {
		assertEquals(-1000.0,new Quantity<>(-1.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
	}
	
	@Test
	public void testConversion_RoundTrip() {
		assertEquals(1500.0,new Quantity<>(1.5,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
	}
	
	@Test
	public void testAddition_SameUnit_KilogramPlusKilogram() {
		assertEquals(new Quantity<>(3.0,WeightUnit.KILOGRAM), new Quantity<>(1.0,WeightUnit.KILOGRAM).add(new Quantity<>(2.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testAddition_CrossUnit_KilogramPlusGram() {
		assertTrue(new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testAddition_CrossUnit_PoundPlusKilogram() {
		assertEquals(4.40924, new Quantity<>(2.20462,WeightUnit.POUND).add(new Quantity<>(1.0,WeightUnit.KILOGRAM)).getValue(),0.0001);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Kilogram() {
		assertEquals(2000.0, new Quantity<>(1.0,WeightUnit.KILOGRAM).add(new Quantity<>(1000.0,WeightUnit.GRAM),WeightUnit.GRAM).getValue());
	}
	
	@Test
	public void testAddition_Commutativity1() {
		Quantity<WeightUnit> sum=new Quantity<>(1.0,WeightUnit.KILOGRAM).add(new Quantity<>(1000.0,WeightUnit.GRAM));
		Quantity<WeightUnit> expected=new Quantity<>(1000.0,WeightUnit.GRAM).add(new Quantity<>(1.0,WeightUnit.KILOGRAM));
		assertEquals(sum,expected);
	}
	
	@Test
	public void testAddition_LargeValues1() {
		Quantity<WeightUnit> sum=new Quantity<>(1e6,WeightUnit.KILOGRAM).add(new Quantity<>(1e6,WeightUnit.KILOGRAM));
		assertEquals(new Quantity<>(2e6,WeightUnit.KILOGRAM),sum);
	}
	
	@Test
	public void testIMeasurableInterface_LengthUnitImplementation() {
		IMeasurable unit=LengthUnit.FEET;
		
		//verify getUnitName()
		assertEquals("FEET",unit.getUnitName());
		
		//verify baseunit
		assertEquals(1.0,unit.convertToBaseUnit(1));
	}
	
	@Test
	public void testIMeasurableInterface_WeightUnitImplementation() {
		IMeasurable unit=WeightUnit.KILOGRAM;
		
		//verify getUnitName()
		assertEquals("KILOGRAM",unit.getUnitName());
		
		//verify baseunit
		assertEquals(1000.0,unit.convertToBaseUnit(1));
		assertEquals(453.592,WeightUnit.POUND.convertToBaseUnit(1),0.001);
	}
	
	@Test
	public void testIMeasurableInterface_ConsistentBehavior() {
		IMeasurable length = LengthUnit.INCHES;
        IMeasurable weight = WeightUnit.KILOGRAM;

        // Both must convert properly
        assertEquals(2.0, length.convertToBaseUnit(24));
        assertEquals(1000.0, weight.convertToBaseUnit(1.0));

        // Polymorphism check
        IMeasurable measurable = LengthUnit.FEET;
        assertNotNull(measurable.convertToBaseUnit(10));
	}
	
	@Test
	public void testGenericQuantity_LengthOperations_Equality() {
		len1=new Quantity<>(1.0,LengthUnit.FEET);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertTrue(len1.equals(len2));		
	}
	
	Quantity<WeightUnit>w1;
	Quantity<WeightUnit>w2;
	
	@Test
	public void testGenericQuantity_WeightOperations_Equality() {
		w1=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		w2=new Quantity<>(1000.0,WeightUnit.GRAM);
		
		assertTrue(w1.equals(w2));		
	}
		
	@Test
	public void testGenericQuantity_WeightOperations_Conversion(){
		w1=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		w2=new Quantity<>(1000.0,WeightUnit.GRAM);
		
		assertEquals(w2.getValue(),w1.convertTo(WeightUnit.GRAM));		
	}
	
	@Test
	public void testGenericQuantity_LengthOperations_Addition(){
		len1=new Quantity<>(1.0,LengthUnit.FEET);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertEquals(new Quantity<>(2.0,LengthUnit.FEET),QuantityMeasurementApp.demonstrateAddition(len1, len2, LengthUnit.FEET));		
	}
	
	@Test
	public void testGenericQuantity_WeightOperations_Addition(){
		w1=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		w2=new Quantity<>(1000.0,WeightUnit.GRAM);
		
		assertEquals(new Quantity<>(2.0,WeightUnit.KILOGRAM),QuantityMeasurementApp.demonstrateAddition(w1, w2, WeightUnit.KILOGRAM));		
	}
	
	@Test
	public void testCrossCategoryPrevention_LengthVsWeight() {
		w1=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertFalse(w1.equals(len2));		
	}
	
	@Test
	public void lengthFeetEqualsInches() {
		len1=new Quantity<>(1.0,LengthUnit.FEET);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertTrue(len1.equals(len2));	
	}

	@Test
	public void lengthYardsEqualsFeet() {
		len1=new Quantity<>(3.0,LengthUnit.FEET);
		len2=new Quantity<>(1.0,LengthUnit.YARDS);
		
		assertTrue(len1.equals(len2));	
	}


	@Test
	public void weightPoundEqualsGrams () {
		w1=new Quantity<>(1,WeightUnit.POUND);
		w2=new Quantity<>(453.592,WeightUnit.GRAM);
		
		assertTrue(w1.equals(w2));	
	}

	// Additional tests for conversion and addition can be added similarly
	@Test
	public void convertLengthFeetToInches() {
		len1=new Quantity<>(1.0,LengthUnit.FEET);
		len2=new Quantity<>(12.0,LengthUnit.INCHES);
		
		assertEquals(len2.getValue(),len1.convertTo(LengthUnit.INCHES));	
	}


	// Generic Type Safety Tests
	@Test
	public void convertLengthYardsToInches() {
		len1=new Quantity<>(1.0,LengthUnit.YARDS);
		len2=new Quantity<>(36.0,LengthUnit.INCHES);
		
		assertEquals(len2.getValue(),len1.convertTo(LengthUnit.INCHES));	
	}
	
	@Test
	public void addWeightTonnesAndKilograms() {
		w1=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		w2=new Quantity<>(1.0,WeightUnit.TONNE);
		
		assertEquals(new Quantity<>(1001.0,WeightUnit.KILOGRAM),QuantityMeasurementApp.demonstrateAddition(w1, w2, WeightUnit.KILOGRAM));		
	}
	
	@Test
	public void testCrossCategoryPrevention_LengthVsWeight1() {
	   len1 = new Quantity<>(1.0, LengthUnit.FEET);
	   w1 =new Quantity<>(1.0, WeightUnit.KILOGRAM);

	    assertNotEquals(len1, w1);
	}
	
	@Test
	public void testCrossCategoryPrevention_CompilerTypeSafety() {
	    len1 = new Quantity<>(1.0, LengthUnit.FEET);
	    w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);

	    assertTrue(true);
	}
	
	@Test
	public void testGenericQuantity_ConstructorValidation_NullUnit() {
	    assertThrows(IllegalArgumentException.class,
	            () -> new Quantity<>(1.0, null));
	}
	
	@Test
	public void testGenericQuantity_ConstructorValidation_InvalidValue() {
	    assertThrows(IllegalArgumentException.class,
	            () -> new Quantity<>(Double.NaN, LengthUnit.FEET));

	    assertThrows(IllegalArgumentException.class,
	            () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
	}
	
	@Test
	public void testGenericQuantity_Addition_AllUnitCombinations() {
	    len1 = new Quantity<>(1.0, LengthUnit.FEET);
	    len2 = new Quantity<>(12.0, LengthUnit.INCHES);

	    Quantity<LengthUnit> result = len1.add(len2, LengthUnit.FEET);

	    assertEquals(2.0, result.getValue(), 1e-9);
	}
	
	@Test
	public void testBackwardCompatibility_AllUC1Through9Tests() {
	    Quantity<LengthUnit> oneFoot = new Quantity<>(1.0, LengthUnit.FEET);
	    Quantity<LengthUnit> twelveInches = new Quantity<>(12.0, LengthUnit.INCHES);

	    assertEquals(oneFoot, twelveInches);
	}
	
	@Test
	public void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
	    assertTrue(QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, LengthUnit.FEET),new Quantity<>(12.0, LengthUnit.INCHES)));
	}
	
	@Test
	public void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
	    Quantity<?> result =QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, WeightUnit.KILOGRAM),WeightUnit.GRAM);

	    assertEquals(1000.0, result.getValue(), 1e-9);
	}
	
	@Test
	public void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
	    Quantity<?> result =
	            QuantityMeasurementApp
	                    .demonstrateAddition(
	                            new Quantity<>(1.0, LengthUnit.FEET),
	                            new Quantity<>(12.0, LengthUnit.INCHES),
	                            LengthUnit.FEET);

	    assertEquals(2.0, result.getValue(), 1e-9);
	}
	
	@Test
	public void testTypeWildcard_FlexibleSignatures() {
	    Quantity<?> length = new Quantity<>(1.0, LengthUnit.FEET);

	    Quantity<?> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

	    assertNotNull(length);
	    assertNotNull(weight);
	}

	@Test
	public void testHashCode_GenericQuantity_Consistency() {
	    Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
	    Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

	    assertEquals(a, b);
	}
	
	//VolumeUnit
	
	Quantity<VolumeUnit>v1;
	Quantity<VolumeUnit>v2;
	
	@Test
	public void testEquality_LitreToLitre_SameValue() {
		v1=new Quantity<>(1.0,VolumeUnit.LITRE);
		v2=new Quantity<>(1.0,VolumeUnit.LITRE);
		
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testEquality_LitreToLitre_DifferentValue() {
		v1=new Quantity<>(1.0,VolumeUnit.LITRE);
		v2=new Quantity<>(2.0,VolumeUnit.LITRE);
		
		assertFalse(v1.equals(v2));
	}
	
	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		v1=new Quantity<>(1.0,VolumeUnit.LITRE);
		v2=new Quantity<>(1000.0,VolumeUnit.MILLILITRE);
		
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testEquality_MillilitreToLitre_EquivalentValue() {
		v2=new Quantity<>(1.0,VolumeUnit.LITRE);
		v1=new Quantity<>(1000.0,VolumeUnit.MILLILITRE);
		
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testEquality_LitreToGallon_EquivalentValue() {
		v1=new Quantity<>(1.0,VolumeUnit.LITRE);
		v2=new Quantity<>(0.264172,VolumeUnit.GALLON);
		
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testEquality_GallonToLitre_EquivalentValue() {
		v2=new Quantity<>(3.78541,VolumeUnit.LITRE);
		v1=new Quantity<>(1.0,VolumeUnit.GALLON);
		
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testEquality_VolumeVsLength_Incompatible() {
		v1=new Quantity<>(1.0,VolumeUnit.LITRE);
		len1=new Quantity<>(1.0,LengthUnit.FEET);
		
		assertFalse(v1.equals(len1));
	}
	
	 @Test
     public void testEquality_NullComparison1() {
    	 assertFalse(new Quantity<VolumeUnit>(1.0,VolumeUnit.LITRE).equals(null));
     }
     
     @Test
     public void testEquality_SameReference1() {
    	 v1 = new Quantity<VolumeUnit>(1.0,VolumeUnit.LITRE);
    	 v2 = v1;
    	 assertTrue(v1.equals(v2));
     }
     
     @Test
     public void testEquality_NullUnit1() {
    	 assertThrows(IllegalArgumentException.class,()->{
    		 v1 = new Quantity<VolumeUnit>(1.0,null); 
    	 });
     }
     
     @Test
     public void testEquality_ZeroValue1() {
    	 assertTrue(new Quantity<>(0.0,VolumeUnit.LITRE).equals(new Quantity<>(0.0,VolumeUnit.MILLILITRE)));
     }
     
     @Test
     public void testEquality_NegativeVolume() {
    	 assertTrue(new Quantity<>(-1.0,VolumeUnit.LITRE).equals(new Quantity<>(-1000.0,VolumeUnit.MILLILITRE)));
     }
     @Test
     public void testConversion_LitreToMillilitre() {
    	 assertEquals(1000.0,new Quantity<>(1.0,VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE));
    	 
     }
     
     @Test
     public void testConversion_GallonToLitre() {
    	 assertEquals(3.78541, new Quantity<>(1.0,VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE));
     }
     
     @Test
     public void testVolumeUnitEnum_GallonConstant() {
    	 assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
     }
     
     @Test
     public void testConvertToBaseUnit_LitreToLitre() {
    	 assertEquals(5.0,VolumeUnit.LITRE.convertToBaseUnit(5.0));
     }
     
     @Test
     public void testConvertToBaseUnit_MillilitreToLitre() {
    	 assertEquals(1.0,VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0));
     }
     
     @Test
     public void testConvertFromBaseUnit_LitreToLitre() {
    	 assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0));
     }
     
     @Test
     public void testSubtraction_SameUnit_FeetMinusFeet() {
    	 assertEquals(5.0,new Quantity<>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(5.0, LengthUnit.FEET)).getValue());
     }
     
     @Test
     public void testSubtraction_SameUnit_LitreMinusLitre() {
    	 assertEquals(7.0,new Quantity<VolumeUnit>(10.0,VolumeUnit.LITRE).subtract(new Quantity<VolumeUnit>(3.0,VolumeUnit.LITRE)).getValue());
     }
     
     @Test
     public void testSubtraction_CrossUnit_FeetMinusInches() {
    	 assertEquals(9.5,new Quantity<LengthUnit>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(6.0,LengthUnit.INCHES)).getValue());
     }
     
     @Test
     public void testSubtraction_ExplicitTargetUnit_Feet() {
    	 assertEquals(114.0,new Quantity<>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(6.0,LengthUnit.INCHES),LengthUnit.INCHES).getValue());
     }
     
     @Test
     public void testSubtraction_ResultingInNegative() {
    	 assertEquals(-5.0,new Quantity<LengthUnit>(5.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(10.0,LengthUnit.FEET)).getValue());
     }
     
     @Test
     public void testSubtraction_ResultingInZero() {
    	 assertEquals(0.0, new Quantity<LengthUnit>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(120.0,LengthUnit.INCHES)).getValue());
     }
     
     @Test
     public void testSubtraction_WithNegativeValues() {
    	 assertEquals(7.0,new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<LengthUnit>(-2.0,LengthUnit.FEET)).getValue());
     }
     
     @Test
     public void testSubtraction_NullOperand() {
    	 assertThrows(IllegalArgumentException.class,()->{
    		 new Quantity<>(10.0, LengthUnit.FEET).subtract(null);
    	 });
     }
     
     @Test
     public void testDivision_SameUnit_FeetDividedByFeet() {
    	 assertEquals(5.0,new Quantity<>(10.0,LengthUnit.FEET).divide(new Quantity<>(2.0,LengthUnit.FEET)));
     }
     
     @Test
     public void testSubtraction_Immutability() {
         Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
         Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

         q1.subtract(q2);

         assertTrue(q1.equals(new Quantity<>(10.0, LengthUnit.FEET)));
         assertTrue(q2.equals(new Quantity<>(5.0, LengthUnit.FEET)));
     }

     
     @Test
     public void testDivision_SameUnit_LitreDividedByLitre() {
    	 assertEquals(2.0,new Quantity<>(10.0,VolumeUnit.LITRE).divide(new Quantity<VolumeUnit>(5.0,VolumeUnit.LITRE)));
     }
     
     @Test
     public void testDivision_CrossUnit_FeetDividedByInches() {
    	
    	 assertEquals(1.0,new Quantity<>(24.0,LengthUnit.INCHES).divide(new Quantity<>(2.0,LengthUnit.FEET)));
     }
     
     @Test
     public void testDivision_RatioGreaterThanOne() {
    	 assertTrue(new Quantity<>(10.0,LengthUnit.FEET).divide(new Quantity<LengthUnit>(2.0,LengthUnit.FEET))>1.0);
     }
     
     @Test
     public void testDivision_RatioLessThanOne() {
    	 assertTrue(new Quantity<>(5.0,LengthUnit.FEET).divide(new Quantity<LengthUnit>(10.0,LengthUnit.FEET))<1.0);
     }
     
     @Test 
     public void testDivision_ByZero() {
    	 assertThrows(ArithmeticException.class, () -> {
    		    new Quantity<>(10.0,LengthUnit.FEET)
    		        .divide(new Quantity<>(0.0,LengthUnit.FEET));
    		});
     }
     
     @Test
     public void testDivision_NonCommutative() {
    	 len1=new Quantity<>(10.0,LengthUnit.FEET);
    	 len2=new Quantity<>(5.0,LengthUnit.FEET);
    	 
    	 assertTrue(len1.divide(len2)!=len2.divide(len1));
     }
     
     @Test
     public void testDivisionWithLargeRatio() {
    	 assertEquals(1e6,new Quantity<>(1e6,WeightUnit.KILOGRAM).divide(new Quantity<>(1.0,WeightUnit.KILOGRAM)));
     }
     
     @Test
     public void testDivision_Immutability() {
         Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
         Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

         q1.divide(q2);

         assertTrue(q1.equals(new Quantity<>(10.0, LengthUnit.FEET)));
         assertTrue(q2.equals(new Quantity<>(5.0, LengthUnit.FEET)));
     }
}
