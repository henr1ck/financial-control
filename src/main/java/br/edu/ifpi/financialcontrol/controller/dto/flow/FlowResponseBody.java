package br.edu.ifpi.financialcontrol.controller.dto.flow;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryResponseBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeResponseBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowResponseBody {
    private String code;
    private String description;
    private BigDecimal value;
    private String note;
    private OffsetDateTime date;
    private CategoryResponseBody category;
    private TypeResponseBody type;
}
