package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.apps.quantitymeasurement.Length.*;

public class QuantityMeasurementAppTest {
	
	Length len1;
	Length len2;
	
	//Feet
	@Test
	public void testFeetEquality_SameValue() {
		 len1 = new Length(1.0,LengthUnit.FEET);
		len2=new Length(1.0,LengthUnit.FEET);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_DifferentValue() {
		 len1 = new Length(1.0,LengthUnit.FEET);
		 len2=new Length(2.0,LengthUnit.FEET);
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_NullComparison() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=null;
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_DifferentClass() {
		 len1=new Length(1.0,LengthUnit.FEET);
		String len2="1.0";
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testFeetEquality_SameReference() {
		 len1=new Length(1.0,LengthUnit.FEET);
		
		assertTrue(len1.equals(len1));
	}
	
	//Inches
	@Test
	public void testInchesEquality_SameValue() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		 len2=new Length(1.0,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_DifferentValue() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		 len2=new Length(2.0,LengthUnit.INCHES);
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_NullComparison() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		 len2=null;
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_DifferentClass() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		String len2="1.0";
		
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchesEquality_SameReference() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		
		assertTrue(len1.equals(len1));
	}
	
	//Length
	@Test
	public void testFeetEquality() {
		len1=new Length(1,LengthUnit.FEET);
		len2=new Length(2,LengthUnit.FEET);
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchEquality() {
		len1 = new Length(11,LengthUnit.INCHES);
		len2 = new Length(11,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testFeetInchesComparison() {
		len1 = new Length(1,LengthUnit.FEET);
		len2 = new Length(12,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	 @Test
	public void testFeetInEquality() {
	   	len1 = new Length(2,LengthUnit.FEET);
	   	len2 = new Length(24,LengthUnit.FEET);
	   	assertFalse(len1.equals(len2));
	 }
	    
     @Test
	 public void testIncheInEquality() {
	   	len1 = new Length(2,LengthUnit.INCHES);
	   	len2 = new Length(24,LengthUnit.INCHES);
	   	assertFalse(len1.equals(len2));
    }
	
	@ParameterizedTest
	@ValueSource(doubles = {1,2,3,4})
	public void testMultipleFeetComparison(double feet) {
		len1 = new Length(feet,LengthUnit.FEET);
		len2 = new Length(feet*12,LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	//Yard
	
	@Test
	public void yardEquals36Inches() {
		assertTrue(new Length(1,LengthUnit.YARDS).equals(new Length(36,LengthUnit.INCHES)));
	}
	
	@Test
	public void centimeterEquals39point3701Inches() {
		assertTrue(new Length(100,LengthUnit.CENTIMETERS).equals(new Length(39.3701,LengthUnit.INCHES)));
	}
	
	@Test
	public void threeFeetEqualsOneYard() {
		assertTrue(new Length(3,LengthUnit.FEET).equals(new Length(1,LengthUnit.YARDS)));
	}

	@Test
	public void thirtyPoint48CmEqualsOneFoot() {
		assertTrue(new Length(30.48,LengthUnit.CENTIMETERS).equals(new Length(1,LengthUnit.FEET)));
	}

	@Test
	public void yardNotEqualToInches () {
		assertFalse(new Length(1.0,LengthUnit.YARDS).equals(new Length(1,LengthUnit.INCHES)));
	}

	@Test
	public void referenceEqualitySameObject() {
		len1 = new Length(1.0, LengthUnit.YARDS);
		len2 = len1;
		assertTrue(len1.equals(len2));
	}

	@Test
	public void equalsReturnsFalseForNull() {
		len1=new Length(1.0,LengthUnit.YARDS);
		assertFalse(len1.equals(null));
	}

	@Test
	public void reflexiveSymmetricAndTransitiveProperty() {
		Length a = new Length(1.0, LengthUnit.YARDS);
	    Length b = new Length(3.0, LengthUnit.FEET);
	    Length c = new Length(36.0, LengthUnit.INCHES);
	    
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
		assertFalse(new Length(10.0,LengthUnit.FEET).equals(new Length(12.0, LengthUnit.FEET)));
	}

	@Test
	public void crossUnitEqualityDemonstrateMethod() {
		Length yard = new Length(1.0, LengthUnit.YARDS);
	    Length inch = new Length(36.0, LengthUnit.INCHES);

	    assertTrue(yard.equals(inch));
	}
	
	@Test
	public void convertFeetToInches() throws InvalidUnitMeasurementException {
		Length lengthInches = QuantityMeasurementApp.demonstrateLengthConversion(3.0, LengthUnit.FEET, LengthUnit.INCHES);
		Length expectedInches = new Length(36.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInches, expectedInches));
	}
	
	@Test
	public void convertYardToInchesUsingOverloadMethod() throws InvalidUnitMeasurementException {
		Length lengthInYard = new Length(2.0, LengthUnit.YARDS);
		Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(lengthInYard, LengthUnit.INCHES);
		Length expected = new Length(72.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expected));
	}
	
	@Test
	void addFeetAndInchesWithTargetUnitInches() throws InvalidUnitMeasurementException {
	    Length Feet = new Length(1.0, LengthUnit.FEET);
	    Length Inches = new Length(24.0, LengthUnit.INCHES);
	    Length output = QuantityMeasurementApp
	            .demonstrateLengthAddition(Feet, Inches, LengthUnit.INCHES);
	    Length result = new Length(3.0, LengthUnit.FEET);
	    assertTrue(output.equals(result));
	}
	
	//Addition
	@Test
	public void testAddition_SameUnit_FeetPlusFeet() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(2.0,LengthUnit.FEET);
		
		Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		Length expected=new Length(3.0,LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_SameUnit_InchPlusInch() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		 len2=new Length(2.0,LengthUnit.INCHES);
		
		Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		Length expected=new Length(3.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_FeetPlusInches() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(12.0,LengthUnit.INCHES);
		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 Length expected=new Length(2.0,LengthUnit.FEET);
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_InchPlusFeet() {
		 len1=new Length(12.0,LengthUnit.INCHES);
		 len2=new Length(1.0,LengthUnit.FEET);
		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 Length expected=new Length(2.0,LengthUnit.FEET);
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_YardPlusFeet() {
		 len1=new Length(1.0,LengthUnit.YARDS);
		 len2=new Length(3.0,LengthUnit.FEET);
		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 Length expected=new Length(2.0,LengthUnit.YARDS);
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_CrossUnit_CentimeterPlusInch() {
		 len1=new Length(2.54,LengthUnit.CENTIMETERS);
		 len2=new Length(1.0,LengthUnit.INCHES);
		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 Length expected=new Length(5.08,LengthUnit.CENTIMETERS);
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_Commutativity() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(12.0,LengthUnit.INCHES);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 
		 Length l1=new Length(12.0,LengthUnit.INCHES);
		 Length l2=new Length(1.0,LengthUnit.FEET);		 
		 Length expected=QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_WithZero() {
		 len1=new Length(5.0,LengthUnit.FEET);
		 len2=new Length(0.0,LengthUnit.INCHES);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 
		 Length expected=new Length(5.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_NegativeValues() {
		 len1=new Length(5.0,LengthUnit.FEET);
		 len2=new Length(-2.0,LengthUnit.FEET);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 
		 Length expected=new Length(3.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_NullSecondOperand() {
		 assertThrows(IllegalArgumentException.class, ()->{
			 len1=new Length(1.0,LengthUnit.FEET);
			 Length sum = QuantityMeasurementApp.demonstrateLengthAddition(len1, null);
		 });		 
	}
	
	@Test
	public void testAddition_LargeValues() {
		 len1=new Length(1e6,LengthUnit.FEET);
		 len2=new Length(1e6,LengthUnit.FEET);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 
		 Length expected=new Length(2e6,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_SmallValues() {
		 len1=new Length(0.001,LengthUnit.FEET);
		 len2=new Length(0.002,LengthUnit.FEET);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2);
		 
		 Length expected=new Length(0.003,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Feet() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(12.0,LengthUnit.INCHES);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2,LengthUnit.FEET);
		 
		 Length expected=new Length(2.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Inches() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(12.0,LengthUnit.INCHES);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2,LengthUnit.INCHES);
		 
		 Length expected=new Length(24.0,LengthUnit.INCHES);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Yards() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(12.0,LengthUnit.INCHES);		 
		 assertEquals(0.667,QuantityMeasurementApp.demonstrateLengthAddition(len2, len1,LengthUnit.YARDS).getValue(),0.001);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Centimeters() {
		 len1=new Length(1.0,LengthUnit.INCHES);
		 len2=new Length(1.0,LengthUnit.INCHES);		
		 
		 assertEquals(5.08,QuantityMeasurementApp.demonstrateLengthAddition(len2, len1,LengthUnit.CENTIMETERS).getValue(),0.01);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		 len1=new Length(2.0,LengthUnit.YARDS);
		 len2=new Length(3.0,LengthUnit.FEET);		
		 
		 assertEquals(3.0,QuantityMeasurementApp.demonstrateLengthAddition(len2, len1,LengthUnit.YARDS).getValue());
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		 len1=new Length(2.0,LengthUnit.YARDS);
		 len2=new Length(3.0,LengthUnit.FEET);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2,LengthUnit.FEET);
		 
		 Length expected=new Length(9.0,LengthUnit.FEET);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Commutativity() {
		 len1=new Length(1.0,LengthUnit.FEET);
		 len2=new Length(12.0,LengthUnit.INCHES);		 
		 Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2,LengthUnit.YARDS);
		 
		 Length expected= QuantityMeasurementApp.demonstrateLengthAddition(new Length(12.0,LengthUnit.INCHES),new Length(1.0,LengthUnit.FEET),LengthUnit.YARDS);		 
		 assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_WithZero() {
		 len1=new Length(5.0,LengthUnit.FEET);
		 len2=new Length(0.0,LengthUnit.INCHES);		 
		 		 
		 assertEquals(1.667,QuantityMeasurementApp.demonstrateLengthAddition(len2, len1,LengthUnit.YARDS).getValue(),0.001);
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_NegativeValues() {
		len1=new Length(5.0, LengthUnit.FEET);
		len2=new Length(-2,LengthUnit.FEET);
		
		Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2,LengthUnit.INCHES);
		Length expected=new Length(36.0,LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		len1=new Length(1.0, LengthUnit.FEET);
		len2=new Length(12.0,LengthUnit.INCHES);
		
		assertThrows(IllegalArgumentException.class, ()-> {						
			Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len1, len2,null);
		});		
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		len1=new Length(1000.0,LengthUnit.FEET);
		len2=new Length(500.0,LengthUnit.FEET);
		Length sum=QuantityMeasurementApp.demonstrateLengthAddition(len2, len1, LengthUnit.INCHES);
		
		Length expected=new Length(18000.0,LengthUnit.INCHES);
		
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sum, expected));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		len1=new Length(12.0,LengthUnit.INCHES);
		len2=new Length(12.0,LengthUnit.INCHES);
		
		assertEquals(0.667 ,QuantityMeasurementApp.demonstrateLengthAddition(len1, len2, LengthUnit.YARDS).getValue(),0.001);		
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
		 Length l1 = new Length(1.0, LengthUnit.FEET);
         Length l2 = new Length(0.1, LengthUnit.FEET);
         Length result = l1.add(l2, LengthUnit.INCHES);
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
		assertTrue(new Length(1.0,LengthUnit.FEET).equals(new Length(12.0,LengthUnit.INCHES)));
	}
	
	@Test
	public void testQuantityLengthRefactored_ConvertTo() {
		assertEquals(new Length(12.0,LengthUnit.INCHES),new Length(1.0,LengthUnit.FEET).convertTo(LengthUnit.INCHES));
	}
	
	@Test
	public void testQuantityLengthRefactored_Add() {
		assertEquals(new Length(2.0,LengthUnit.FEET),QuantityMeasurementApp.demonstrateLengthAddition(new  Length(1.0,LengthUnit.FEET), new Length(12.0,LengthUnit.INCHES), LengthUnit.FEET));
	}
	
	@Test
	public void testQuantityLengthRefactored_AddWithTargetUnit() {
		assertEquals(new Length(0.667,LengthUnit.YARDS),QuantityMeasurementApp.demonstrateLengthAddition(new  Length(1.0,LengthUnit.FEET), new Length(12.0,LengthUnit.INCHES), LengthUnit.YARDS));
	}
	
	@Test
	public void testQuantityLengthRefactored_NullUnit() {
		assertThrows(IllegalArgumentException.class, ()->{
			new Length(1.0,null);
		});
	}
	
	@Test
	public void testQuantityLengthRefactored_InvalidValue() {
		assertThrows(IllegalArgumentException.class, ()->{
			new Length(Double.NaN,LengthUnit.FEET);
		});
	}
	
	//UC-9
	
	@Test
	public void testEquality_KilogramToKilogram_SameValue() {
		assertTrue(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Weight(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_KilogramToKilogram_DifferentValue() {
		assertFalse(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Weight(2.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_KilogramToGram_EquivalentValue() {
		assertTrue(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Weight(1000.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testEquality_GramToKilogram_EquivalentValue() {
		assertTrue(new Weight(1000.0,WeightUnit.GRAM).equals(new Weight(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_WeightVsLength_Incompatible() {
		assertFalse(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Length(1.0,LengthUnit.FEET)));
	}
	
	@Test
	public void testEquality_NullComparison() {
		assertFalse(new Weight(1.0,WeightUnit.KILOGRAM).equals(null));
	}
	
	@Test
	public void testEquality_SameReference() {
		assertTrue(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Weight(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_NullUnit() {
		assertThrows(IllegalArgumentException.class, ()->{
			new Weight(1.0,null);
		});
	}
	
	@Test
	public void testEquality_TransitiveProperty() {
		Weight a = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight b = new Weight(1000.0, WeightUnit.GRAM);
	    Weight c = new Weight(1.0, WeightUnit.KILOGRAM);
	    	    
	    //Transitive
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(c));
	    assertTrue(a.equals(c));
	}
	
	@Test
	public void testEquality_ZeroValue() {
		assertTrue(new Weight(0.0,WeightUnit.KILOGRAM).equals(new Weight(0.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testEquality_NegativeWeight() {
		assertTrue(new Weight(-1.0,WeightUnit.KILOGRAM).equals(new Weight(-1000.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testEquality_LargeWeightValue() {
		assertTrue(new Weight(1000000.0,WeightUnit.GRAM).equals(new Weight(1000.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testEquality_SmallWeightValue() {
		assertTrue(new Weight(0.001,WeightUnit.KILOGRAM).equals(new Weight(1.0,WeightUnit.GRAM)));
	}
	
	@Test
	public void testConversion_PoundToKilogram() {
		assertEquals(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(2.20462,WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));
	}
	
	@Test
	public void testConversion_KilogramToPound() {
		assertEquals(new Weight(2.20462,WeightUnit.POUND),new Weight(1.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND));
	}
	
	@Test
	public void testConversion_SameUnit() {
		assertEquals(new Weight(5.0,WeightUnit.KILOGRAM),new Weight(5.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM));
	}
	
	@Test
	public void testConversion_ZeroValue() {
		assertEquals(new Weight(0.0,WeightUnit.GRAM),new Weight(0.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
	}
	
	@Test
	public void testConversion_NegativeValue() {
		assertEquals(new Weight(-1000.0,WeightUnit.GRAM),new Weight(-1.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
	}
	
	@Test
	public void testConversion_RoundTrip() {
		assertEquals(new Weight(1.5,WeightUnit.KILOGRAM),new Weight(1.5,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
	}
	
	@Test
	public void testAddition_SameUnit_KilogramPlusKilogram() {
		assertEquals(new Weight(3.0,WeightUnit.KILOGRAM), new Weight(1.0,WeightUnit.KILOGRAM).add(new Weight(2.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testAddition_CrossUnit_KilogramPlusGram() {
		assertTrue(new Weight(1.0,WeightUnit.KILOGRAM).equals(new Weight(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testAddition_CrossUnit_PoundPlusKilogram() {
		assertEquals(new Weight(4.40924,WeightUnit.POUND), new Weight(2.20462,WeightUnit.POUND).add(new Weight(1.0,WeightUnit.KILOGRAM)));
	}
	
	@Test
	public void testAddition_ExplicitTargetUnit_Kilogram() {
		assertEquals(new Weight(4.40924,WeightUnit.POUND), QuantityMeasurementApp.demonstrateLengthAddition(new Weight(1.0,WeightUnit.KILOGRAM), new Weight(1000.0,WeightUnit.GRAM), WeightUnit.GRAM));
	}
	
	@Test
	public void testAddition_Commutativity1() {
		Weight sum=new Weight(1.0,WeightUnit.KILOGRAM).add(new Weight(1000.0,WeightUnit.GRAM));
		Weight expected=new Weight(1000.0,WeightUnit.GRAM).add(new Weight(1.0,WeightUnit.KILOGRAM));
		assertEquals(sum,expected);
	}
	
	@Test
	public void testAddition_LargeValues1() {
		Weight sum=new Weight(1e6,WeightUnit.KILOGRAM).add(new Weight(1e6,WeightUnit.KILOGRAM));
		assertEquals(new Weight(2e6,WeightUnit.KILOGRAM),sum);
	}
}
