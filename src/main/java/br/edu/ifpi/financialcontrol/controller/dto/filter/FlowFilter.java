package br.edu.ifpi.financialcontrol.controller.dto.filter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowFilter {
    @Parameter(description = "ID do tipo de fluxo", example = "2")
    private Long typeId;

    @Parameter(description = "ID da categoria", example = "4")
    private Long categoryId;

    @Parameter(description = "Valor mínimo do fluxo", example = "45.67")
    private BigDecimal minValue;

    @Parameter(description = "Valor máximo do fluxo", example = "999.99")
    private BigDecimal maxValue;

    @Parameter(description = "Data limite inicial", example = "2022-12-01T14:00:00-03:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime initialDate;

    @Parameter(description = "Data limite final", example = "2023-02-01T18:00:00-03:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime finalDate;
}
