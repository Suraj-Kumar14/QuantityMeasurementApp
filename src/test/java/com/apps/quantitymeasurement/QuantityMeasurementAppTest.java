package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.apps.quantitymeasurement.Length.*;
import com.apps.quantitymeasurement.QuantityMeasurementApp.Feet;
import com.apps.quantitymeasurement.QuantityMeasurementApp.Inches;

public class QuantityMeasurementAppTest {
	
	Length len1;
	Length len2;
	
	//Feet
	@Test
	public void testFeetEquality_SameValue() {
		Feet feet1=new Feet(1.0);
		Feet feet2=new Feet(1.0);
		assertTrue(feet1.equals(feet2));
	}
	
	@Test
	public void testFeetEquality_DifferentValue() {
		Feet feet1=new Feet(1.0);
		Feet feet2=new Feet(2.0);
		
		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	public void testFeetEquality_NullComparison() {
		Feet feet1=new Feet(1.0);
		Feet feet2=null;
		
		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	public void testFeetEquality_DifferentClass() {
		Feet feet1=new Feet(1.0);
		String feet2="1.0";
		
		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	public void testFeetEquality_SameReference() {
		Feet feet1=new Feet(1.0);
		
		assertTrue(feet1.equals(feet1));
	}
	
	//Inches
	@Test
	public void testInchesEquality_SameValue() {
		Inches inch1=new Inches(1.0);
		Inches inch2=new Inches(1.0);
		assertTrue(inch1.equals(inch2));
	}
	
	@Test
	public void testInchesEquality_DifferentValue() {
		Inches inch1=new Inches(1.0);
		Inches inch2=new Inches(2.0);
		
		assertFalse(inch1.equals(inch2));
	}
	
	@Test
	public void testInchesEquality_NullComparison() {
		Inches inch1=new Inches(1.0);
		Inches inch2=null;
		
		assertFalse(inch1.equals(inch2));
	}
	
	@Test
	public void testInchesEquality_DifferentClass() {
		Inches inch1=new Inches(1.0);
		String inch2="1.0";
		
		assertFalse(inch1.equals(inch2));
	}
	
	@Test
	public void testInchesEquality_SameReference() {
		Inches inch1=new Inches(1.0);
		
		assertTrue(inch1.equals(inch1));
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
}
