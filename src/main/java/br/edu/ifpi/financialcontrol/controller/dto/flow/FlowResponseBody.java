package br.edu.ifpi.financialcontrol.controller.dto.flow;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryResponseBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeResponseBody;
import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowResponseBody {
    @JsonView(FlowView.Simple.class)
    private String code;

    @JsonView(FlowView.Simple.class)
    private String description;

    @JsonView(FlowView.Simple.class)
    private BigDecimal value;

    @JsonView(FlowView.Simple.class)
    private TypeResponseBody type;

    private CategoryResponseBody category;

    private String note;

    private OffsetDateTime date;
}
