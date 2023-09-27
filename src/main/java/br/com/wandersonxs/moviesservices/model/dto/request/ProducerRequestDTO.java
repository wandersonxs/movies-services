package br.com.wandersonxs.moviesservices.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerRequestDTO {


    private Long id;

    @Schema(name = "name", type = "string", format = "string", description = "Producer's name", example = "Bruce Lee")
    @NotEmpty(message = "[name] is required")
    private String name;

}
