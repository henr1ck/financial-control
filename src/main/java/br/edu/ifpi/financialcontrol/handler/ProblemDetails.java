package br.edu.ifpi.financialcontrol.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProblemDetails {
    @Schema(example = "Generic name for error")
    private String title;
    @Schema(example = "Specific Error details")
    private String detail;
    @Schema(example = "400")
    private Integer status;
    private LocalDateTime timestamp;
    @Schema(hidden = true)
    private List<FieldDetails> fields;
}
