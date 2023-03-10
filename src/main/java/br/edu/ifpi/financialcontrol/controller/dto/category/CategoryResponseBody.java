package br.edu.ifpi.financialcontrol.controller.dto.category;

import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonView(FlowView.Simple.class)
public class CategoryResponseBody {
    @Schema(example = "4")
    private Long id;
    @Schema(example = "Manutenção")
    private String name;
}
