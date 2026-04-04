package com.app.microservices.quantity_service.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.microservices.quantity_service.dto.QuantityInputDTO;
import com.app.microservices.quantity_service.dto.QuantityMeasurementDTO;
import com.app.microservices.quantity_service.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

	private final IQuantityMeasurementService quantityMeasurementService;

	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}

	private static final String EX_FEET_INCH = """
			{
			  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
			  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
			}""";

	private static final String EX_YARD_FEET = """
			{
			  "thisQuantityDTO": {"value": 1.0, "unit": "YARDS", "measurementType": "LengthUnit"},
			  "thatQuantityDTO": {"value": 3.0, "unit": "FEET", "measurementType": "LengthUnit"}
			}""";

	private static final String EX_GALLON_LITRE = """
			{
			  "thisQuantityDTO": {"value": 1.0, "unit": "GALLON", "measurementType": "VolumeUnit"},
			  "thatQuantityDTO": {"value": 3.785, "unit": "LITRE", "measurementType": "VolumeUnit"}
			}""";

	private static final String EX_TEMP = """
			{
			  "thisQuantityDTO": {"value": 212.0, "unit": "FAHRENHEIT", "measurementType": "TemperatureUnit"},
			  "thatQuantityDTO": {"value": 100.0, "unit": "CELSIUS", "measurementType": "TemperatureUnit"}
			}""";

	private static final String EX_WITH_TARGET = """
			{
			  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
			  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"},
			  "targetQuantityDTO": {"value": 0.0, "unit": "INCHES", "measurementType": "LengthUnit"}
			}""";

	@GetMapping
	public String welcome() {
		return "Welcome! It is working";
	}

	@PostMapping("/compare")
	@Operation(summary = "Compare two quantities", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {
			@ExampleObject(name = "Feet = 12 Inches", value = EX_FEET_INCH),
			@ExampleObject(name = "Yard = 3 Feet", value = EX_YARD_FEET),
			@ExampleObject(name = "Gallon = Litres", value = EX_GALLON_LITRE),
			@ExampleObject(name = "212°F = 100℃", value = EX_TEMP) })))
	public ResponseEntity<QuantityMeasurementDTO> performComparison(
			@Valid @RequestBody QuantityInputDTO quantityInputDTO, @RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.compare(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), username));
	}

	@PostMapping("/convert")
	@Operation(summary = "Convert quantity to target unit", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {
			@ExampleObject(name = "Feet -> Inches", value = EX_FEET_INCH),
			@ExampleObject(name = "Yard -> Feet", value = EX_YARD_FEET),
			@ExampleObject(name = "Gallon -> Litres", value = EX_GALLON_LITRE),
			@ExampleObject(name = "212°F -> 100℃", value = EX_TEMP) })))
	public ResponseEntity<QuantityMeasurementDTO> performConversion(
			@Valid @RequestBody QuantityInputDTO quantityInputDTO, @RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.convert(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), username));
	}

	@PostMapping("/add")
	public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO quantityInputDTO,
			@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.add(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), username));
	}

	@PostMapping("/add-with-target-unit")
	public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(
			@Valid @RequestBody QuantityInputDTO quantityInputDTO, @RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.add(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), quantityInputDTO.getTargetQuantityDTO(), username));
	}

	@PostMapping("/subtract")
	public ResponseEntity<QuantityMeasurementDTO> performSubtraction(
			@Valid @RequestBody QuantityInputDTO quantityInputDTO, @RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.subtract(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), username));
	}

	@PostMapping("/subtract-with-target-unit")
	public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(
			@Valid @RequestBody QuantityInputDTO quantityInputDTO, @RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.subtract(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), quantityInputDTO.getTargetQuantityDTO(), username));
	}

	@PostMapping("/divide")
	public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO quantityInputDTO,
			@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.divide(quantityInputDTO.getThisQuantityDTO(),
				quantityInputDTO.getThatQuantityDTO(), username));
	}

	@GetMapping("/history")
	public ResponseEntity<List<QuantityMeasurementDTO>> getUserHistory(@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.getHistoryByUsername(username));
	}

	@GetMapping("/history/operation/{operation}")
	public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation,
			@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.getOperationHistoryByUsername(operation, username));
	}

	@GetMapping("/history/type/{type}")
	public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistoryByType(@PathVariable String type,
			@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.getMeasurementByTypeAndUsername(type, username));
	}

	@GetMapping("/count/{operation}")
	public ResponseEntity<Long> getOperationCount(@PathVariable String operation,
			@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.getOperationCountByUsername(operation, username));
	}

	@GetMapping("/history/errored")
	public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations(
			@RequestHeader("X-User-Name") String username) {

		return ResponseEntity.ok(quantityMeasurementService.getErrorHistoryByUsername(username));
	}
}