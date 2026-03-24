package com.app.quantitymeasurement.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.QuantityModel;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
//import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.*;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

	@Autowired
	private QuantityMeasurementRepository repository;

	private enum Operation {
		COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE;
	}

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		// 1. Map
		QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

		// validate
		validateModels(m1, m2);

		// 3. Create Domain Objects
		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		double val1 = q1.convertTo(q1.getUnit());
		double val2 = q2.convertTo(q1.getUnit());

		// 4. Use the equals method from Quantity.java
		boolean isEqual = Double.compare(val1, val2) == 0;

		// 5. Save to Repository
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
				thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
				thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), Operation.COMPARE.name(),
				isEqual ? 1.0 : 0.0, thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), null, false,
				null);
		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		// 1. Map
		QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

		// 3. Create Domain Objects
		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());

		double value1 = q1.convertTo(m2.getUnit());
		// 4. save to repository
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
				thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
				thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), Operation.CONVERT.name(), value1,
				thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), null, false, null);

		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO, Operation.ADD);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.ADD);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO, Operation.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		;
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO, Operation.DIVIDE);
	}

	/**
	 * Helper to map DTO (Strings) to Model (Actual Unit Enums)
	 */
	QuantityModel<IMeasurable> mapToModel(QuantityDTO dto) {
		if (dto == null) {
			throw new QuantityMeasurementException("Quantity data cannot be null");
		}

		String type = dto.getMeasurementType();
		String unitName = dto.getUnit();
		IMeasurable unit;
		try {
			switch (type) {
			case "LengthUnit":
				unit = LengthUnit.valueOf(unitName);
				break;
			case "VolumeUnit":
				unit = VolumeUnit.valueOf(unitName);
				break;
			case "WeightUnit":
				unit = WeightUnit.valueOf(unitName);
				break;
			case "TemperatureUnit":
				unit = TemperatureUnit.valueOf(unitName);
				break;
			default:
				throw new IllegalArgumentException("Invalid Measurement Category: " + type);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unit '" + unitName + "' is not valid for " + type);
		}
		return new QuantityModel<>(dto.getValue(), unit);
	}

	/**
	 * Validation logic as requested in the flow diagram
	 */
	private void validateModels(QuantityModel<?> m1, QuantityModel<?> m2) {
		if (m1 == null || m2 == null) {
			throw new QuantityMeasurementException("Measurement operands cannot be null");
		}

		if (m1.getUnit().getClass() != m2.getUnit().getClass()) {
			throw new QuantityMeasurementException("Incompatible types: " + m1.getUnit().getClass().getSimpleName()
					+ " vs " + m2.getUnit().getClass().getSimpleName());
		}

		if (!Double.isFinite(m1.getValue()) || !Double.isFinite(m2.getValue())) {
			throw new QuantityMeasurementException("Invalid numeric value provided");
		}
	}

	/**
	 * This will helper method reuse for all method
	 */
	private QuantityMeasurementDTO executeArithmetic(QuantityDTO d1, QuantityDTO d2, QuantityDTO target,
			Operation opType) {
		// 1. Map
		QuantityModel<IMeasurable> m1 = mapToModel(d1);
		QuantityModel<IMeasurable> m2 = mapToModel(d2);
		QuantityModel<IMeasurable> mT = mapToModel(target);

		// 2. Validate
		validateModels(m1, m2);

//		if (mT != null)
		validateModels(m1, mT);

		// 3. Domain Call (Quantity.java handles the actual Math)
		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		Quantity<IMeasurable> result;
		if (opType.name().contains("ADD")) {
			result = q1.add(q2, mT.getUnit());
		} else if (opType.name().contains("SUBTRACT")) {
			result = q1.subtract(q2, mT.getUnit());
		} else {
			double value = q1.divide(q2, mT.getUnit());
			result = new Quantity<IMeasurable>(value, q1.getUnit());
		}

		// 4. Extract & Save (Persistence)
		double resVal = result.getValue();
		String resUnit = result.getUnit().toString();

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(d1.getValue(), d1.getUnit(),
				d1.getMeasurementType(), d2.getValue(), d2.getUnit(), d2.getMeasurementType(), opType.name(), resVal,
				resUnit, d1.getMeasurementType(), null, false, null);

		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public List<QuantityMeasurementDTO> getOpeartionHistory(String operation) {
		validateOperation(operation);
		return new QuantityMeasurementDTO().fromList(repository.findByOperation(operation));
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementByType(String type) {
		validateTypes(type);
		return new QuantityMeasurementDTO().fromList(repository.findByThisMeasurementType(type));
	}

	@Override
	public Long getOperationCount(String operation) {
		validateOperation(operation);
		return repository.countByOperationAndIsErrorFalse(operation);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return new QuantityMeasurementDTO().fromList(repository.findByIsErrorTrue());
	}

	private void validateOperation(String operation) {
		try {
			switch (operation) {
			case "ADD":
				break;
			case "SUBTRACT":
				break;
			case "COMPARE":
				break;
			case "CONVERT":
				break;
			case "DIVIDE":
				break;
			default:
				throw new UnsupportedOperationException("Invalid Operation: " + operation);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unit type is not valid");
		}
	}

	private void validateTypes(String type) {
		try {
			switch (type) {
			case "LengthUnit":
				break;
			case "VolumeUnit":
				break;
			case "WeightUnit":
				break;
			case "TemperatureUnit":
				break;
			default:
				throw new QuantityMeasurementException("Invalid Measurement Category: " + type);
			}
		} catch (IllegalArgumentException e) {
			throw new QuantityMeasurementException("Unit Type is not valid for");
		}
	}
}