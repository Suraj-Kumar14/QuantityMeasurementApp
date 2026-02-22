package com.apps.quantitymeasurement;
public class QuantityMeasurementApp {

	//Inner class to represent Feet measurement
	public static class Feet{
		
		private final double value;
		
		public Feet(double value) {
			this.value=value;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			//reference check
			if(this==obj) {
				return true;
			}
			
			//null check
			if(obj==null) {
			return false;
			}
			
			//type check
			if(obj.getClass()!=this.getClass()) {
				return false;
			}
			
			return Double.compare(this.value, ((Feet)obj).value) == 0;		
		}
	}
	
	//Inner class to represent Inches measurement
	public static class Inches{
		
		private final double value;
		
		public Inches(double value) {
			this.value=value;
		}
		
		@Override 
		public boolean equals(Object obj) {
			if(this==obj) {
				return true;
			}
			
			if(obj==null) {
				return false;
			}
			
			if(this.getClass()!=obj.getClass()) {
				return false;
			}
			
			return Double.compare(this.value, ((Inches)obj).value)==0;
			
		}
	}
		
	//static method to demonstrate feet equality check
	public static void demonstrateFeetEquality() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		
		System.out.println("Equal"+"("+feet1.equals(feet2)+")");
	}
	
	//static method to demonstrate Inches equality check
	public static void demonstrateInchesEquality() {
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(1.0);
		
		System.out.println("Equal"+"("+inch1.equals(inch2)+")");
	}
	
	// generic method to demonstrate Length equality check
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.compare(length2);
	}
		
	//static method to demonstrate Feet and Inches comparison
	public static void demonstrateFeetInchComparison() {
		
		Length _1Feet=new Length(1,Length.LengthUnit.FEET);
		Length _12Inches=new Length(12,Length.LengthUnit.INCHES);
		System.out.println("1 Feet = 12 Inches: "+_1Feet.equals(_12Inches));
		
		Length _3Feet = new Length(3,Length.LengthUnit.FEET);
		Length _36Inches=new Length(36,Length.LengthUnit.INCHES);
		System.out.println("3 Feet = 36 Inches: "+_3Feet.equals(_36Inches));
		
		Length _1Inches=new Length(1,Length.LengthUnit.INCHES);
		Length _Foot=new Length(1,Length.LengthUnit.FEET);
		System.out.println("1 Inch == 1 foot : "+_1Inches.equals(_Foot));
		
	}
		
	//main method to demonstrate Inches equality check
	public static void main(String[] args) {
		demonstrateFeetEquality();
		demonstrateInchesEquality();
		
		demonstrateFeetInchComparison();
		System.out.println(demonstrateLengthEquality(new Length(1,Length.LengthUnit.FEET),new Length(12,Length.LengthUnit.INCHES)));
	}

	
}
