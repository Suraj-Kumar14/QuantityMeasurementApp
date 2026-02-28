package com.apps.quantitymeasurement;

import java.util.function.DoubleBinaryOperator;

public final class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value!");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Invalid unit!");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

 
    public double convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        if (!this.unit.getClass().equals(targetUnit.getClass())) {
            throw new IllegalArgumentException("Incompatible unit categories");
        }

        double baseValue = unit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(baseValue);
    }

  
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double converted = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double converted = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

 
    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired) {

        if (other == null) {
            throw new IllegalArgumentException("Operand cannot be null");
        }

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException(
                    "Cannot perform arithmetic on different measurement categories");
        }

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }

        if (targetUnitRequired) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            if (!this.unit.getClass().equals(targetUnit.getClass())) {
                throw new IllegalArgumentException(
                        "Target unit must belong to same measurement category");
            }
        }
    }

    private double performBaseArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation) {

        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        return operation.compute(baseValue1, baseValue2);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

      private enum ArithmeticOperation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (Math.abs(b) < 1e-10) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?>)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(baseValue1 - baseValue2) < 0.0001;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    /* ===================== MAIN TEST ===================== */

    public static void main(String[] args) {

        Quantity<LengthUnit> length1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> length2 = new Quantity<>(120.0, LengthUnit.INCHES);

        System.out.println("Equal lengths? " + length1.equals(length2));
        System.out.println("Total length in FEET: " + length1.add(length2));

        Quantity<WeightUnit> weight1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weight2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("Equal weights? " + weight1.equals(weight2));

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        System.out.println("Equal volumes? " + v1.equals(v2));

        System.out.println("Division test: " + length1.divide(new Quantity<>(2.0, LengthUnit.FEET)));
    }
}