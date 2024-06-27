package com.tms.ticketing_system.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseEntity<T> {
	private String message;
	private T data;
	private List<T> datas;
	public ResponseEntity(String message, T data) {
		super();
		this.message = message;
		this.data = data;
	}
	
	
	public ResponseEntity(String message, List<T> datas) {
		super();
		this.message = message;
		this.datas = datas;
	}



	public List<T> getDatas() {
		return datas;
	}



	public void setDatas(List<T> datas) {
		this.datas = datas;
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
