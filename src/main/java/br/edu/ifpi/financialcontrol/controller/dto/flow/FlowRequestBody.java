package br.edu.ifpi.financialcontrol.controller.dto.flow;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryIdRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeIdRequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowRequestBody {
    @Schema(example = "Suporte celular")
    @NotBlank @NotNull
    private String description;

    @Schema(example = "19.99")
    @DecimalMin(value = "0.01") @NotNull
    private BigDecimal value;

    @Schema(example = "Para a aula de Flutter")
    private String note;

    @Valid @NotNull
    private CategoryIdRequestBody category;

    @Valid @NotNull
    private TypeIdRequestBody type;
}
