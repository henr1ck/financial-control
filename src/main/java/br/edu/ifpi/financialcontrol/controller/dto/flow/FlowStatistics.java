package br.edu.ifpi.financialcontrol.controller.dto.flow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class FlowStatistics {
    @Schema(example = "2")
    private Long amount;
    @Schema(example = "50")
    private BigDecimal totalValue;
    private Date date;
}
