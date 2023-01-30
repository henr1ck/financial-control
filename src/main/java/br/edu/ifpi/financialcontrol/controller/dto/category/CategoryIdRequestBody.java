package br.edu.ifpi.financialcontrol.controller.dto.category;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryIdRequestBody {
    @NotNull @Positive
    private Long id;
}
