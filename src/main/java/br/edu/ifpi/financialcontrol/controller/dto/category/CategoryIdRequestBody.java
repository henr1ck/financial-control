package br.edu.ifpi.financialcontrol.controller.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryIdRequestBody {
    @Schema(example = "4")
    @NotNull @Positive
    private Long id;
}
