package br.edu.ifpi.financialcontrol.controller.dto.type;

import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonView(FlowView.Simple.class)
public class TypeResponseBody {
    private Long id;
    private String name;
}
