package com.app.microservices.quantity_service.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.microservices.quantity_service.dto.QuantityDTO;
import com.app.microservices.quantity_service.dto.QuantityMeasurementDTO;
import com.app.microservices.quantity_service.dto.QuantityModel;
import com.app.microservices.quantity_service.entity.QuantityMeasurementEntity;
import com.app.microservices.quantity_service.exception.QuantityMeasurementException;
import com.app.microservices.quantity_service.quantity.Quantity;
import com.app.microservices.quantity_service.repository.QuantityMeasurementRepository;
import com.app.microservices.quantity_service.unit.IMeasurable;
import com.app.microservices.quantity_service.unit.LengthUnit;
import com.app.microservices.quantity_service.unit.TemperatureUnit;
import com.app.microservices.quantity_service.unit.VolumeUnit;
import com.app.microservices.quantity_service.unit.WeightUnit;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

	@Autowired
	private QuantityMeasurementRepository repository;

	private enum Operation {
		COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE;
	}

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username) {
		QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

		validateModels(m1, m2);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		double val1 = q1.convertTo(q1.getUnit());
		double val2 = q2.convertTo(q1.getUnit());

		boolean isEqual = Double.compare(val1, val2) == 0;

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
				thisQuantityDTO.getValue(),
				thisQuantityDTO.getUnit(),
				thisQuantityDTO.getMeasurementType(),
				thatQuantityDTO.getValue(),
				thatQuantityDTO.getUnit(),
				thatQuantityDTO.getMeasurementType(),
				Operation.COMPARE.name(),
				isEqual ? 1.0 : 0.0,
				thisQuantityDTO.getUnit(),
				thisQuantityDTO.getMeasurementType(),
				null,
				false,
				null
		);

		entity.setUsername(username);
		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username) {
		QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());

		double value1 = q1.convertTo(m2.getUnit());

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
				thisQuantityDTO.getValue(),
				thisQuantityDTO.getUnit(),
				thisQuantityDTO.getMeasurementType(),
				thatQuantityDTO.getValue(),
				thatQuantityDTO.getUnit(),
				thatQuantityDTO.getMeasurementType(),
				Operation.CONVERT.name(),
				value1,
				thatQuantityDTO.getUnit(),
				thisQuantityDTO.getMeasurementType(),
				null,
				false,
				null
		);

		entity.setUsername(username);
		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO, Operation.ADD, username);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO, String username) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.ADD, username);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO, Operation.SUBTRACT, username);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO, String username) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.SUBTRACT, username);
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO, Operation.DIVIDE, username);
	}

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

	private void validateModels(QuantityModel<?> m1, QuantityModel<?> m2) {
		if (m1 == null || m2 == null) {
			throw new QuantityMeasurementException("Measurement operands cannot be null");
		}

		if (m1.getUnit().getClass() != m2.getUnit().getClass()) {
			throw new QuantityMeasurementException(
					"Incompatible types: " + m1.getUnit().getClass().getSimpleName()
							+ " vs " + m2.getUnit().getClass().getSimpleName());
		}

		if (!Double.isFinite(m1.getValue()) || !Double.isFinite(m2.getValue())) {
			throw new QuantityMeasurementException("Invalid numeric value provided");
		}
	}

	private QuantityMeasurementDTO executeArithmetic(QuantityDTO d1, QuantityDTO d2, QuantityDTO target,
			Operation opType, String username) {

		QuantityModel<IMeasurable> m1 = mapToModel(d1);
		QuantityModel<IMeasurable> m2 = mapToModel(d2);
		QuantityModel<IMeasurable> mT = mapToModel(target);

		validateModels(m1, m2);
		validateModels(m1, mT);

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

		double resVal = result.getValue();
		String resUnit = result.getUnit().toString();

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
				d1.getValue(),
				d1.getUnit(),
				d1.getMeasurementType(),
				d2.getValue(),
				d2.getUnit(),
				d2.getMeasurementType(),
				opType.name(),
				resVal,
				resUnit,
				d1.getMeasurementType(),
				null,
				false,
				null
		);

		entity.setUsername(username);
		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public List<QuantityMeasurementDTO> getHistoryByUsername(String username) {
		return new QuantityMeasurementDTO().fromList(repository.findByUsernameOrderByCreatedAtDesc(username));
	}

	@Override
	public List<QuantityMeasurementDTO> getOperationHistoryByUsername(String operation, String username) {
		validateOperation(operation);
		return new QuantityMeasurementDTO().fromList(
				repository.findByOperationAndUsernameOrderByCreatedAtDesc(operation, username)
		);
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementByTypeAndUsername(String type, String username) {
		validateTypes(type);
		return new QuantityMeasurementDTO().fromList(
				repository.findByThisMeasurementTypeAndUsernameOrderByCreatedAtDesc(type, username)
		);
	}

	@Override
	public Long getOperationCountByUsername(String operation, String username) {
		validateOperation(operation);
		return repository.countByOperationAndUsernameAndIsErrorFalse(operation, username);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistoryByUsername(String username) {
		return new QuantityMeasurementDTO().fromList(repository.findByUsernameAndIsErrorTrue(username));
	}

	private void validateOperation(String operation) {
		switch (operation) {
		case "ADD":
		case "SUBTRACT":
		case "COMPARE":
		case "CONVERT":
		case "DIVIDE":
			break;
		default:
			throw new UnsupportedOperationException("Invalid Operation: " + operation);
		}
	}

	private void validateTypes(String type) {
		switch (type) {
		case "LengthUnit":
		case "VolumeUnit":
		case "WeightUnit":
		case "TemperatureUnit":
			break;
		default:
			throw new QuantityMeasurementException("Invalid Measurement Category: " + type);
		}
	}
}