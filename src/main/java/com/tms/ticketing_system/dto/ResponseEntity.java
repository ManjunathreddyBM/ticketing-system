package com.tms.ticketing_system.dto;

import org.springframework.http.HttpStatus;

public class ResponseEntity<T> {
	private String message;
	private T data;
	
	public ResponseEntity(String message, T data) {
		super();
		this.message = message;
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
