package br.com.wandersonxs.moviesservices.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError<T> {
    @JsonProperty("timestamp")
    private String timestamp = null;

    @JsonProperty("httpStatus")
    private Integer httpStatus = null;

    @JsonProperty("errorCode")
    private Integer errorCode = null;

    @JsonProperty("message")
    private T message = null;

    @JsonProperty("details")

    @Valid
    private List<Object> details = null;

    @JsonProperty("traceId")
    private String traceId = null;
}