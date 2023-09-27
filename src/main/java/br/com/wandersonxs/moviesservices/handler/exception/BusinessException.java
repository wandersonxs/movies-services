package br.com.wandersonxs.moviesservices.handler.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
public class BusinessException extends Exception {

  @Serial
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
