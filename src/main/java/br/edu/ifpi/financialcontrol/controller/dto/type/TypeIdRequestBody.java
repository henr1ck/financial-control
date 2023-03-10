package br.edu.ifpi.financialcontrol.controller.dto.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeIdRequestBody {
    @Schema(example = "2")
    @NotNull @Positive
    private Long id;
}
