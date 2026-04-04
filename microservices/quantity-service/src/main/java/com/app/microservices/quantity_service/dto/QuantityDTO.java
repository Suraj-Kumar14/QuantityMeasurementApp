package com.app.microservices.quantity_service.dto;

import java.util.logging.Logger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

 interface IMeasurableUnit {
	
	public String getUnitName();
	public String getMeasurementType();
}

 @Data
 @Schema(description = "A quantity with a value and unit")
public class QuantityDTO {
	 
	 private static final Logger logger = Logger.getLogger(
			 QuantityDTO.class.getName());

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
	
	@NotNull(message = "Value cannot be empty")
	@Schema(example = "1.0")
    private double value;
	
	@NotNull(message = "Unit cannot be null")
	@Schema(example = "FEET", allowableValues = {
			"FEET", "INCHES", "YARDS", "CENTIMETERS", 
			"LITER", "MILLILITER", "GALLON",
			"MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE",
			"CELSIUS", "FAHRENHEIT"
	})
    private String unit;
	
	@NotNull(message = "Measurement type cannot be null")
	@Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
			message = "Measurement type must be one of: LengthUnit, VolumeUnit,WeightUnit, TemperatureUnit")
	@Schema(example = "LengthUnit", allowableValues = {
			"LengthUnit", "VolumeUnit", "WeightUnit", "TemperatureUnit"
	})
    private String measurementType;
	
	public QuantityDTO() {		
	}
	
	public QuantityDTO(double value, IMeasurableUnit unit) {
	     this.value = value;
	     this.unit = unit.toString();
	     this.measurementType = unit.getClass().getSimpleName();
	 }
	   
	public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

	@AssertTrue(message = "Unit must be valid for the specified measurement type")
	public boolean isValidUnit() {
	    if (unit == null || measurementType == null) {
	        return false;
	    }

	    try {
	        switch (measurementType) {
	            case "LengthUnit":
	                LengthUnit.valueOf(unit);
	                return true;

	            case "WeightUnit":
	                WeightUnit.valueOf(unit);
	                return true;

	            case "VolumeUnit":
	                VolumeUnit.valueOf(unit);
	                return true;

	            case "TemperatureUnit":
	                TemperatureUnitUnit.valueOf(unit);
	                return true;

	            default:
	                return false;
	        }
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	}

}