package com.app.microservices.quantity_service.dto;

public enum OperationType {
	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE,
	COMPARE,
	CONVERT;
	
	public String getDisplayName() {
		return this.name().toLowerCase();
	}
}