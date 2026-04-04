package com.app.quantitymeasurement.dto;

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
