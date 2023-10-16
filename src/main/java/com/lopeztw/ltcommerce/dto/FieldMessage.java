package com.lopeztw.ltcommerce.dto;

public class FieldMessage {
	private String fieldName; // Nome do campo ( {name} {price} .... 
	private String message; // Mensagem do error
	
	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}
	
	
}
