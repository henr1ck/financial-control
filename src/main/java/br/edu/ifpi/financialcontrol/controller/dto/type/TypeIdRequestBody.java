package br.edu.ifpi.financialcontrol.controller.dto.type;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeIdRequestBody {
    @NotNull @Positive
    private Long id;
}
