package br.edu.ifpi.financialcontrol.controller.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeIdRequestBody {
    @NotNull @Positive
    private Long id;
}
