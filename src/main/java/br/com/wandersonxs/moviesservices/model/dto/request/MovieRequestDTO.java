package br.com.wandersonxs.moviesservices.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class MovieRequestDTO {

    private Long id;

    @Schema(name = "title", type = "string", format = "string", description = "Movie's title", required = true, example = "Final Countdown")
    @NotNull(message = "[title] is required")
    private String title;

    @Schema(name = "studio", type = "string", format = "string", description = "Movie's studio", required = true, example = "Warner Bros.")
    @NotNull(message = "[studio] is required")
    private String studio;

    @Schema(name = "producersId", type = "array", format = "array", description = "Movie's producers id", required = true)
    @NotNull(message = "[producers] is required")
    private List<ProducerRequestDTO> producers;

    @Schema(name = "year", type = "integer", format = "integer", description = "Movie's year", required = true, example = "2000")
    @NotNull(message = "[year] is required")
    private Integer year;

    @Schema(name = "winner", type = "boolean", format = "boolean", description = "Movie's winner", required = true, example = "true")
    @NotNull(message = "[studio] is required")
    private boolean winner;
}
