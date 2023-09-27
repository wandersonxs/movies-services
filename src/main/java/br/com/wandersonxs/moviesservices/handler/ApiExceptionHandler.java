package br.com.wandersonxs.moviesservices.handler;

import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import br.com.wandersonxs.moviesservices.handler.exception.DataBaseException;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.Date;

@ControllerAdvice
@RestController
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HttpMessageConversionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleJsonParseException(Exception ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Timestamp(new Date().getTime()).toString())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundHandler(NotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {DataBaseException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Timestamp(new Date().getTime()).toString())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleValidationException(BusinessException businessException) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Timestamp(new Date().getTime()).toString())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message(businessException.getMessages())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
