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
		len1=new Length(1,Length.LengthUnit.FEET);
		len2=new Length(2,Length.LengthUnit.FEET);
		assertFalse(len1.equals(len2));
	}
	
	@Test
	public void testInchEquality() {
		len1 = new Length(11,Length.LengthUnit.INCHES);
		len2 = new Length(11,Length.LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	@Test
	public void testFeetInchesComparison() {
		len1 = new Length(1,Length.LengthUnit.FEET);
		len2 = new Length(12,Length.LengthUnit.INCHES);
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
		len1 = new Length(feet,Length.LengthUnit.FEET);
		len2 = new Length(feet*12,Length.LengthUnit.INCHES);
		assertTrue(len1.equals(len2));
	}
	
	//Yard
	
	@Test
	public void yardEquals36Inches() {
		assertTrue(new Length(1,Length.LengthUnit.YARDS).equals(new Length(36,Length.LengthUnit.INCHES)));
	}
	
	@Test
	public void centimeterEquals39point3701Inches() {
		assertTrue(new Length(100,Length.LengthUnit.CENTIMETERS).equals(new Length(39.3701,Length.LengthUnit.INCHES)));
	}
	
	@Test
	public void threeFeetEqualsOneYard() {
		assertTrue(new Length(3,Length.LengthUnit.FEET).equals(new Length(1,Length.LengthUnit.YARDS)));
	}

	@Test
	public void thirtyPoint48CmEqualsOneFoot() {
		assertTrue(new Length(30.48,Length.LengthUnit.CENTIMETERS).equals(new Length(1,Length.LengthUnit.FEET)));
	}

	@Test
	public void yardNotEqualToInches () {
		assertFalse(new Length(1.0,Length.LengthUnit.YARDS).equals(new Length(1,Length.LengthUnit.INCHES)));
	}

	@Test
	public void referenceEqualitySameObject() {
		len1 = new Length(1.0, Length.LengthUnit.YARDS);
		len2 = len1;
		assertTrue(len1.equals(len2));
	}

	@Test
	public void equalsReturnsFalseForNull() {
		len1=new Length(1.0,Length.LengthUnit.YARDS);
		assertFalse(len1.equals(null));
	}

	@Test
	public void reflexiveSymmetricAndTransitiveProperty() {
		Length a = new Length(1.0, Length.LengthUnit.YARDS);
	    Length b = new Length(3.0, Length.LengthUnit.FEET);
	    Length c = new Length(36.0, Length.LengthUnit.INCHES);
	    
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
		assertFalse(new Length(10.0,Length.LengthUnit.FEET).equals(new Length(12.0, Length.LengthUnit.FEET)));
	}

	@Test
	public void crossUnitEqualityDemonstrateMethod() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
	    Length inch = new Length(36.0, Length.LengthUnit.INCHES);

	    assertTrue(yard.equals(inch));
	}
	
	@Test
	public void convertFeetToInches() throws InvalidUnitMeasurementException {
		Length lengthInches = QuantityMeasurementApp.demonstrateLengthConversion(3.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		Length expectedInches = new Length(36.0,Length.LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInches, expectedInches));
	}
	
	@Test
	public void convertYardToInchesUsingOverloadMethod() throws InvalidUnitMeasurementException {
		Length lengthInYard = new Length(2.0, Length.LengthUnit.YARDS);
		Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(lengthInYard, LengthUnit.INCHES);
		Length expected = new Length(72.0,Length.LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expected));
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
}
