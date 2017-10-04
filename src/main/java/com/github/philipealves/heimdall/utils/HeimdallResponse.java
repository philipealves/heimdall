package com.github.philipealves.heimdall.utils;

import java.io.Serializable;

public class HeimdallResponse<T> implements Serializable {

	private static final long serialVersionUID = -4663863121544749901L;

	private Integer code;
	private String message;
	private T result;

	public HeimdallResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public HeimdallResponse(T result, Integer code, String message) {
		super();
		this.result = result;
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
