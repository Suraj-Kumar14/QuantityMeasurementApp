package com.apps.quantitymeasurement.service;

import java.util.function.Supplier;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.entity.QuantityModel;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.IMeasurable;
import com.apps.quantitymeasurement.unit.LengthUnit;
import com.apps.quantitymeasurement.unit.TemperatureUnit;
import com.apps.quantitymeasurement.unit.VolumeUnit;
import com.apps.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final double COMPARE_TOLERANCE = 0.0001;
    private static final double DIVIDE_TOLERANCE = 0.0000001;

    private final IQuantityMeasurementRepository repository;

    private enum ArithmeticOperation {
        ADD, SUBTRACT, DIVIDE
    }

    private enum OperationType {
        COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE
    }

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    @Override
    public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return execute(OperationType.COMPARE, thisQuantityDTO, thatQuantityDTO, () -> {
            QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

            validateCompatibility(thisQuantityDTO, thatQuantityDTO);

            double thisBaseValue = thisQuantity.getUnit().convertToBaseUnit(thisQuantity.getValue());
            double thatBaseValue = thatQuantity.getUnit().convertToBaseUnit(thatQuantity.getValue());

            boolean result = Math.abs(thisBaseValue - thatBaseValue) < COMPARE_TOLERANCE;
            saveComparisonResult(thisQuantityDTO, thatQuantityDTO, result);
            return result;
        });
    }

    @Override
    public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return execute(OperationType.CONVERT, thisQuantityDTO, thatQuantityDTO, () -> {
            QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

            validateCompatibility(thisQuantityDTO, thatQuantityDTO);

            double convertedValue = convertTo(thisQuantity, thatQuantity.getUnit());
            QuantityDTO result = new QuantityDTO(
                    convertedValue,
                    thatQuantity.getUnit().toString(),
                    thatQuantityDTO.getMeasurementType()
            );

            saveQuantityResult(thisQuantityDTO, thatQuantityDTO, OperationType.CONVERT, result);
            return result;
        });
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return add(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO);
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return executeArithmetic(
                thisQuantityDTO,
                thatQuantityDTO,
                targetUnitDTO,
                OperationType.ADD,
                ArithmeticOperation.ADD
        );
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return subtract(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return executeArithmetic(
                thisQuantityDTO,
                thatQuantityDTO,
                targetUnitDTO,
                OperationType.SUBTRACT,
                ArithmeticOperation.SUBTRACT
        );
    }

    @Override
    public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return execute(OperationType.DIVIDE, thisQuantityDTO, thatQuantityDTO, () -> {
            QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

            validateArithmeticOperands(thisQuantityDTO, thatQuantityDTO, null, false);

            double result = performArithmetic(thisQuantity, thatQuantity, ArithmeticOperation.DIVIDE);
            saveDoubleResult(thisQuantityDTO, thatQuantityDTO, result);
            return result;
        });
    }

    private QuantityDTO executeArithmetic(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO,
            OperationType operationType,
            ArithmeticOperation arithmeticOperation) {

        return execute(operationType, thisQuantityDTO, thatQuantityDTO, () -> {
            QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);
            QuantityModel<IMeasurable> targetUnit = getQuantityModel(targetUnitDTO);

            validateArithmeticOperands(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, true);

            double resultBaseValue = performArithmetic(thisQuantity, thatQuantity, arithmeticOperation);
            double resultValue = targetUnit.getUnit().convertFromBaseUnit(resultBaseValue);

            QuantityDTO result = new QuantityDTO(
                    resultValue,
                    targetUnit.getUnit().toString(),
                    targetUnitDTO.getMeasurementType()
            );

            saveQuantityResult(thisQuantityDTO, thatQuantityDTO, operationType, result);
            return result;
        });
    }

    private <T> T execute(
            OperationType operationType,
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            Supplier<T> action) {

        try {
            return action.get();
        } catch (ArithmeticException e) {
            saveError(thisQuantityDTO, thatQuantityDTO, operationType, e);
            throw e;
        } catch (IllegalArgumentException e) {
            saveError(thisQuantityDTO, thatQuantityDTO, operationType, e);
            throw new QuantityMeasurementException("Error during " + operationType.name().toLowerCase(), e);
        } catch (Exception e) {
            saveError(thisQuantityDTO, thatQuantityDTO, operationType, e);
            throw new QuantityMeasurementException("Error during " + operationType.name().toLowerCase(), e);
        }
    }

    private void saveComparisonResult(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, boolean result) {
        QuantityMeasurementEntity entity = createBaseEntity(thisQuantityDTO, thatQuantityDTO, OperationType.COMPARE.name());
        entity.resultValue = result ? 1.0 : 0.0;
        entity.resultUnit = "BOOLEAN";
        entity.resultMeasurementType = normalizeMeasurementType(thisQuantityDTO.getMeasurementType());
        repository.save(entity);
    }

    private void saveQuantityResult(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            OperationType operationType,
            QuantityDTO result) {

        QuantityMeasurementEntity entity = createBaseEntity(thisQuantityDTO, thatQuantityDTO, operationType.name());
        entity.resultValue = result.getValue();
        entity.resultUnit = result.getUnit();
        entity.resultMeasurementType = result.getMeasurementType();
        repository.save(entity);
    }

    private void saveDoubleResult(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, double result) {
        QuantityMeasurementEntity entity = createBaseEntity(thisQuantityDTO, thatQuantityDTO, OperationType.DIVIDE.name());
        entity.resultValue = result;
        entity.resultUnit = thisQuantityDTO.getUnit();
        entity.resultMeasurementType = thisQuantityDTO.getMeasurementType();
        repository.save(entity);
    }

    private void saveError(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            OperationType operationType,
            Exception e) {

        QuantityMeasurementEntity entity = createBaseEntity(thisQuantityDTO, thatQuantityDTO, operationType.name());
        entity.isError = true;
        entity.errorMessage = e.getMessage();
        repository.save(entity);
    }

    private QuantityMeasurementEntity createBaseEntity(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            String operation) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        if (thisQuantityDTO != null) {
            entity.thisValue = thisQuantityDTO.getValue();
            entity.thisUnit = thisQuantityDTO.getUnit();
            entity.thisMeasurementType = thisQuantityDTO.getMeasurementType();
        }

        if (thatQuantityDTO != null) {
            entity.thatValue = thatQuantityDTO.getValue();
            entity.thatUnit = thatQuantityDTO.getUnit();
            entity.thatMeasurementType = thatQuantityDTO.getMeasurementType();
        }

        entity.operation = operation;
        return entity;
    }

    private <U extends IMeasurable> double convertTo(QuantityModel<U> thisQuantity, U targetUnit) {
        double baseValue = thisQuantity.getUnit().convertToBaseUnit(thisQuantity.getValue());
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("QuantityDTO must not be null");
        }

        if (quantity.getUnit() == null || quantity.getMeasurementType() == null) {
            throw new IllegalArgumentException("Unit and measurement type must not be null");
        }

        String measurementType = quantity.getMeasurementType().trim().toUpperCase();
        String unitName = quantity.getUnit().trim().toUpperCase();

        IMeasurable unit;

        switch (measurementType) {
            case "LENGTH":
            case "LENGTHUNIT":
                unit = LengthUnit.valueOf(unitName);
                break;
            case "WEIGHT":
            case "WEIGHTUNIT":
                unit = WeightUnit.valueOf(unitName);
                break;
            case "VOLUME":
            case "VOLUMEUNIT":
                unit = VolumeUnit.valueOf(unitName);
                break;
            case "TEMPERATURE":
            case "TEMPERATUREUNIT":
                unit = TemperatureUnit.valueOf(unitName);
                break;
            default:
                throw new IllegalArgumentException("Unsupported measurement type: " + measurementType);
        }

        return new QuantityModel<>(quantity.getValue(), unit);
    }

    private void validateArithmeticOperands(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO,
            boolean targetUnitRequired) {

        if (thisQuantityDTO == null || thatQuantityDTO == null) {
            throw new IllegalArgumentException("Operands must not be null");
        }

        validateCompatibility(thisQuantityDTO, thatQuantityDTO);

        if (!Double.isFinite(thisQuantityDTO.getValue()) || !Double.isFinite(thatQuantityDTO.getValue())) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }

        QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
        QuantityModel<IMeasurable> other = getQuantityModel(thatQuantityDTO);

        if (!thisQuantity.getUnit().supportsArithmetic() || !other.getUnit().supportsArithmetic()) {
            throw new IllegalArgumentException("Arithmetic is not supported for this measurement category");
        }

        if (targetUnitRequired) {
            if (targetUnitDTO == null) {
                throw new IllegalArgumentException("Target unit is required");
            }

            validateCompatibility(thisQuantityDTO, targetUnitDTO);

            QuantityModel<IMeasurable> targetUnit = getQuantityModel(targetUnitDTO);
            if (!targetUnit.getUnit().supportsArithmetic()) {
                throw new IllegalArgumentException("Target unit does not support arithmetic");
            }
        }
    }

    private <U extends IMeasurable> double performArithmetic(
            QuantityModel<U> thisQuantity,
            QuantityModel<U> other,
            ArithmeticOperation operation) {

        double thisBaseValue = thisQuantity.getUnit().convertToBaseUnit(thisQuantity.getValue());
        double otherBaseValue = other.getUnit().convertToBaseUnit(other.getValue());

        switch (operation) {
            case ADD:
                return thisBaseValue + otherBaseValue;
            case SUBTRACT:
                return thisBaseValue - otherBaseValue;
            case DIVIDE:
                if (Math.abs(otherBaseValue) < DIVIDE_TOLERANCE) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return thisBaseValue / otherBaseValue;
            default:
                throw new IllegalArgumentException("Unsupported arithmetic operation");
        }
    }

    private void validateCompatibility(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        String thisType = normalizeMeasurementType(thisQuantityDTO.getMeasurementType());
        String thatType = normalizeMeasurementType(thatQuantityDTO.getMeasurementType());

        if (!thisType.equalsIgnoreCase(thatType)) {
            throw new IllegalArgumentException(
                    "Incompatible measurement types: "
                            + thisQuantityDTO.getMeasurementType()
                            + " and "
                            + thatQuantityDTO.getMeasurementType()
            );
        }
    }

    private String normalizeMeasurementType(String measurementType) {
        String type = measurementType.trim().toUpperCase();
        if (type.endsWith("UNIT")) {
            type = type.substring(0, type.length() - 4);
        }
        return type;
    }
}