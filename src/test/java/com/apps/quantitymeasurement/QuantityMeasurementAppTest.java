package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.apps.quantitymeasurement.Length.LengthUnit;
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
}
