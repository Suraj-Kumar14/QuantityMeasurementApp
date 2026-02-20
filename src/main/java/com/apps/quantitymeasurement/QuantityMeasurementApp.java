package com.apps.quantitymeasurement;
public class QuantityMeasurementApp {

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
	
	public static void main(String[] args) {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		
		System.out.println("Equal"+"("+feet1.equals(feet2)+")");
	}

}
