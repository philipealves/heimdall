package com.github.philipealves.heimdall.utils;

public enum ResponseCode {
	
	SUCCESS(0, "Serviço executado com sucesso!"),
	ALERT(-1, "Alerta"),
	ERROR(-2, "Erro ao executar serviço."),
	ACCESS_DENIED(-3, "Acesso Negado");

	private Integer code;
	private String message;
	
	private ResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
