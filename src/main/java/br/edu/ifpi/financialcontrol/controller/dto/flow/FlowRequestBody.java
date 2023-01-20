package br.edu.ifpi.financialcontrol.controller.dto.flow;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryIdRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeIdRequestBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowRequestBody {
    private String description;
    private BigDecimal value;
    private String note;
    private CategoryIdRequestBody category;
    private TypeIdRequestBody type;
}
