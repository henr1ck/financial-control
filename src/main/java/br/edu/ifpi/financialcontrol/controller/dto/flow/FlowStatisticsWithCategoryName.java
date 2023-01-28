package br.edu.ifpi.financialcontrol.controller.dto.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class FlowStatisticsWithCategoryName {
    private String category;
    private Long amount;
    private BigDecimal totalValue;
    private Double average;
    private BigDecimal minValue;
    private BigDecimal maxValue;
}
