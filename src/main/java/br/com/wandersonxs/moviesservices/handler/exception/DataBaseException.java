package br.com.wandersonxs.moviesservices.handler.exception;

import java.io.Serial;

public class DataBaseException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public DataBaseException(String entityName) {
		super("Erro ao tentar criar " + entityName);
	}
}
