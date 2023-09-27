package br.com.wandersonxs.moviesservices.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError<T> {
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("httpStatus")
    private Integer httpStatus;

    @JsonProperty("message")
    private T message;


}