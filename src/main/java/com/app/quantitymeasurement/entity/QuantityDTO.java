package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.unit.IMeasurable;

 interface IMeasurableUnit {
	
	public String getUnitName();
	public String getMeasurementType();
}

public class QuantityDTO {

	 public enum LengthUnit implements IMeasurableUnit{
		
		FEET, INCHES, YARDS, CENTIMETERS, METERS;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}		
		
	}
	
	 public enum WeightUnit implements IMeasurableUnit{		
		 MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE;
		 
		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}		
		
	}
	
	 public enum VolumeUnit implements IMeasurableUnit{
		
		LITRE, MILLILITRE, GALLON;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}		
		
	}
	
	 public enum TemperatureUnitUnit implements IMeasurableUnit{
		
		CELSIUS, FAHRENHEIT, KELVIN;	

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}		
	}
	
	
    private double value;
    private String unit;
    private String measurementType;

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public QuantityDTO(double value, IMeasurable unit) {
        this.value = value;
        this.unit = unit.toString();
        this.measurementType = unit.getClass().getSimpleName();
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    @Override
    public String toString() {
        return "QuantityDTO [value=" + value + ", unit=" + unit + ", measurementType=" + measurementType + "]";
    }
}