package br.edu.ifpi.financialcontrol.controller.dto.flow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class FlowStatisticsWithCategoryName {
    @Schema(example = "Saúde")
    private String category;
    @Schema(example = "4")
    private Long amount;
    @Schema(example = "500")
    private BigDecimal totalValue;
    @Schema(example = "125")
    private Double average;
    @Schema(example = "50")
    private BigDecimal minValue;
    @Schema(example = "200")
    private BigDecimal maxValue;
}
