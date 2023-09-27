package br.com.wandersonxs.moviesservices.resource.api;

import br.com.wandersonxs.moviesservices.handler.ApiError;
import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
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
@RequestMapping(value = "/v1/movies")
public interface MoviesApi {

    @Operation(summary = "Get movies", description = "Get all movies optionally filtering them by title, studio, year or winner", tags = {"movies"})
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovieResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<Page<MovieResponseDTO>> getMovies(
            @Parameter(in = ParameterIn.QUERY, description = "Movie's title", schema = @Schema()) @Valid @RequestParam(value = "title", required = false) String title,
            @Parameter(in = ParameterIn.QUERY, description = "Movie's studio", schema = @Schema()) @Valid @RequestParam(value = "studio", required = false) String studio,
            @Parameter(in = ParameterIn.QUERY, description = "Movie's year", schema = @Schema()) @Valid @RequestParam(value = "year", required = false) Integer year,
            @Parameter(in = ParameterIn.QUERY, description = "Movie's winner", schema = @Schema()) @Valid @RequestParam(value = "winner", required = false) Boolean winner,
            Pageable page) throws NotFoundException;

    @Operation(summary = "Get movies by id", description = "Get movies by id", tags = {"movies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<MovieResponseDTO> getMovieById(
            @Parameter(in = ParameterIn.PATH, description = "Movie id", required = true, schema = @Schema()) @PathVariable("id") Long id) throws NotFoundException;


    @Operation(summary = "Add movie", description = "Add movie",  tags = {"movies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<MovieResponseDTO> addMovie(
            @Parameter(in = ParameterIn.DEFAULT, description = "Movie's data", required = true, schema = @Schema()) @Valid @RequestBody MovieRequestDTO movieRequestDTO) throws NotFoundException;

    @Operation(summary = "Update movie", description = "Update movie", tags = {"movies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<MovieResponseDTO> updateMovie(
            @Parameter(in = ParameterIn.PATH, description = "Movie id", required = true, schema = @Schema()) @PathVariable("id") Long id,
            @Parameter(in = ParameterIn.DEFAULT, description = "Movie's id", required = true, schema = @Schema()) @Valid @RequestBody MovieRequestDTO movieRequestDTO) throws NotFoundException;


    @Operation(summary = "Delete movie", description = "Delete movie", tags = {"movies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class)))})
    @RequestMapping(value = "/{id}",
            produces = {"*/*"},
            method = RequestMethod.DELETE)
    void deleteMovie(
            @Parameter(in = ParameterIn.PATH, description = "Movie id", required = true, schema = @Schema()) @PathVariable("id") Long id) throws NotFoundException;


}


