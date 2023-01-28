package br.edu.ifpi.financialcontrol.controller.dto.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class FlowStatistics {
    private Long amount;
    private BigDecimal totalValue;
    private Date date;
}
