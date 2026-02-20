package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp.Feet;
import com.apps.quantitymeasurement.QuantityMeasurementApp.Inches;

public class QuantityMeasurementAppTest {
	
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
	
}
