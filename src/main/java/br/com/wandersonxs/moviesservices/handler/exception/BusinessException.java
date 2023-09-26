package br.com.wandersonxs.moviesservices.handler.exception;

import lombok.Data;

import java.util.List;

@Data
public class BusinessException extends Exception {

  private static final long serialVersionUID = 1L;

  private List<String> messages;

  public BusinessException() {
    super();
  }

  public BusinessException(String errorMessage) {
    super(errorMessage);
    messages = List.of(errorMessage);
  }

  public BusinessException(List<String> messages) {
    this.messages = messages;
  }

}
