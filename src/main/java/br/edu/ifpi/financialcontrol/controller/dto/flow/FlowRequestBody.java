package br.edu.ifpi.financialcontrol.controller.dto.flow;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryIdRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeIdRequestBody;
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
    @NotBlank @NotNull
    private String description;
    @DecimalMin(value = "0.01") @NotNull
    private BigDecimal value;
    private String note;
    @Valid @NotNull
    private CategoryIdRequestBody category;
    @Valid @NotNull
    private TypeIdRequestBody type;
}
