package br.edu.ifpi.financialcontrol.controller.dto.flow;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryResponseBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeResponseBody;
import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "cfae6d4a-xpto-4def-8ece-94d55290d6bb")
    @JsonView(FlowView.Simple.class)
    private String code;

    @Schema(example = "Suporte celular")
    @JsonView(FlowView.Simple.class)
    private String description;

    @Schema(example = "19.99")
    @JsonView(FlowView.Simple.class)
    private BigDecimal value;

    @JsonView(FlowView.Simple.class)
    private TypeResponseBody type;

    @JsonView(FlowView.Simple.class)
    private CategoryResponseBody category;

    @Schema(example = "2023-03-05T18:46:17.576Z")
    @JsonView(FlowView.Simple.class)
    private OffsetDateTime date;

    @Schema(example = "Para a aula de Flutter")
    private String note;
}
