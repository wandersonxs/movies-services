package br.com.wandersonxs.moviesservices.handler.exception;

public class DataBaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataBaseException(String entityName) {
		super("Erro ao tentar criar " + entityName);
	}
}
