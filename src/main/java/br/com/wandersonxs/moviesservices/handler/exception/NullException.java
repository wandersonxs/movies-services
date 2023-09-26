package br.com.wandersonxs.moviesservices.handler.exception;

public class NullException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullException() {
		super("Existem campos orbigatórios vazios.");
	}
}
