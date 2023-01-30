package br.edu.ifpi.financialcontrol.controller.dto.filter;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FlowStatisticsFilter {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date initialDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date finalDate;
}
