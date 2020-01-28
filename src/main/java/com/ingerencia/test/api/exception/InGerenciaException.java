package com.ingerencia.test.api.exception;

@SuppressWarnings("serial")
public class InGerenciaException extends RuntimeException {

	public InGerenciaException() {}

	public InGerenciaException(String message, Throwable cause) {
		super(message, cause);
	}

	public InGerenciaException(String message) {
		super(message);
	}

	public InGerenciaException(Throwable cause) {
		super(cause);
	}
	
	
}
