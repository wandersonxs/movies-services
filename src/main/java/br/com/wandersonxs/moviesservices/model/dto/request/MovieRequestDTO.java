package br.com.wandersonxs.moviesservices.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class MovieRequestDTO {

    private Long id;

    @Schema(name = "title", type = "string", format = "string", description = "Movie's title",  example = "Final Countdown")
    @NotEmpty(message = "[title] is required")
    private String title;

    @Schema(name = "studio", type = "string", format = "string", description = "Movie's studio", example = "Warner Bros.")
    @NotEmpty(message = "[studio] is required")
    private String studio;

    @Schema(name = "producersId", type = "array", format = "array", description = "Movie's producers id")
    @NotEmpty(message = "[producers] is required")
    private List<ProducerRequestDTO> producers;

    @Schema(name = "year", type = "integer", format = "integer", description = "Movie's year", example = "2000")
    @NotEmpty(message = "[year] is required")
    private Integer year;

    @Schema(name = "winner", type = "boolean", format = "boolean", description = "Movie's winner", example = "true")
    @NotEmpty(message = "[studio] is required")
    private boolean winner;
}
