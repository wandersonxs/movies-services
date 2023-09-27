package br.com.wandersonxs.moviesservices.resource.api;

import br.com.wandersonxs.moviesservices.handler.ApiError;
import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.request.ProducerRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducersWinnersResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Validated
@RequestMapping(value = "/v1/producers")
public interface ProducersApi {


    @Operation(summary = "Get producers winners", description = "Get all producers winners more than once and applying the requirement test.", tags = {"producers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProducerResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(
            value = "/movies/winners",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<ProducersWinnersResponseDTO> getProducersWinners();

    @Operation(summary = "Get producers", description = "Get all producers by filter name", tags = {"producers"})
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProducerResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<Page<ProducerResponseDTO>> getProducers(
            @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "name", required = false) String name,
            Pageable page) throws NotFoundException;

    @Operation(summary = "Get producers by id", description = "Get producers by id", tags = {"producers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProducerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<ProducerResponseDTO> getProducerById(
            @Parameter(in = ParameterIn.PATH, description = "Producer id", required = true, schema = @Schema()) @PathVariable("id") Long id) throws NotFoundException;


    @Operation(summary = "Add producer", description = "Add producer", tags = {"producers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProducerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ProducerResponseDTO> addProducer(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ProducerRequestDTO producerRequestDTO) throws BusinessException, NotFoundException;

    @Operation(summary = "Update producer", description = "Update producer", tags = {"producers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProducerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<ProducerResponseDTO> updateProducer(
            @Parameter(in = ParameterIn.PATH, description = "Producer id", required = true, schema = @Schema()) @PathVariable("id") Long id,
            @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ProducerRequestDTO producerRequestDTO) throws NotFoundException, BusinessException;


    @Operation(summary = "Delete producer", description = "Delete producer", tags = {"producers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(value = "/{id}",
            produces = {"*/*"},
            method = RequestMethod.DELETE)
    void deleteProducer(
            @Parameter(in = ParameterIn.PATH, description = "Producer id", required = true, schema = @Schema()) @PathVariable("id") Long id) throws NotFoundException, BusinessException;


}


