package com.apps.quantitymeasurement.entity;

import com.apps.quantitymeasurement.unit.IMeasurable;

public class QuantityDTO {

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