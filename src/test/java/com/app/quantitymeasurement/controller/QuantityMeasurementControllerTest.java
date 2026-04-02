package com.app.quantitymeasurement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.app.quantitymeasurement.config.JwtFilter;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(QuantityMeasurementController.class) 
@Import(JacksonAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)  // Disable all security filters for testing
public class QuantityMeasurementControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IQuantityMeasurementService service;
	
	@MockBean
	private JwtFilter jwtFilter;
	
	private ObjectMapper objectMapper;
	private QuantityInputDTO inputDto;
	private QuantityMeasurementDTO result;
	
	
	@BeforeEach
	public void setup() {
		
		objectMapper =  new ObjectMapper();
		inputDto = new QuantityInputDTO();
		
		inputDto.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
		inputDto.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LengthUnit"));
		
		result = new QuantityMeasurementDTO();
		
		result.setThisValue(inputDto.getThisQuantityDTO().getValue());
		result.setThisUnit(inputDto.getThisQuantityDTO().getUnit());
		result.setThisMeasurementType(inputDto.getThisQuantityDTO().getMeasurementType());
		result.setThatValue(inputDto.getThatQuantityDTO().getValue());
		result.setThatUnit(inputDto.getThatQuantityDTO().getUnit());
		result.setThatMeasurementType(inputDto.getThatQuantityDTO().getMeasurementType());
	}
	
	
	@Test
	public void testCompareQuantities_Success() throws Exception {
		result.setOperation("Compare");
		result.setResultString("true"); 
		result.setResultValue(0.0);
		result.setResultUnit(null);
		result.setResultMeasurementType(null);
		result.error = false;
		
		Mockito.when(
				service.compare(inputDto.getThisQuantityDTO(), inputDto.getThatQuantityDTO()))
			   .thenReturn(result); 
		
		
		mockMvc.perform(post("/api/v1/quantities/compare")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(objectMapper.writeValueAsString(inputDto))
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.resultString").value("true"));
	}
	
	
	@Test
	public void testAddQuantities_Success() throws Exception {
		result.setOperation("add");
		result.setResultValue(2.0);
		result.setResultUnit("FEET");
		result.setResultMeasurementType("LengthUnit");
		result.error = false;
		
		Mockito.when(
			    service.add(inputDto.getThisQuantityDTO(), inputDto.getThatQuantityDTO()))
			    .thenReturn (result);
		
	
		mockMvc.perform(
			    post("/api/v1/quantities/add")
			   .param("targetUnit", "FEET")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(objectMapper.writeValueAsString(inputDto))
		)
		.andExpect(status().isOk()) 
		.andExpect(jsonPath("$.resultValue").value(2.0));
	}
	
	
	@Test
	public void testGetOperationHistory_Success() throws Exception {
		// Mock the service layer to return an empty list for the operation history
		
		Mockito.when(
				service.getOpeartionHistory("COMPARE"))
			   .thenReturn(java.util.Collections.emptyList());
	
		mockMvc.perform(
				get("/api/v1/quantities/history/operation/COMPARE")
			   .contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(0))
		.andReturn();
	}
		
	
	@Test
	public void testGetOperationCount_Success() throws Exception {
		// Mock the service layer to return a count of 0 for the operation count
		
		Mockito.when(
				service.getOperationCount("COMPARE"))
				.thenReturn(0L);
		
		mockMvc.perform(
				get("/api/v1/quantities/count/COMPARE")
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(content().string("0"))
		.andReturn();
	}
}