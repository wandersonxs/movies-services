package br.com.wandersonxs.moviesservices.handler.exception;

public class ApiException extends Exception {

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(Integer code, String msg) {
        super(msg);
    }

    public ApiException(String msg, final Throwable cause) {
        super(msg, cause);
    }
}
