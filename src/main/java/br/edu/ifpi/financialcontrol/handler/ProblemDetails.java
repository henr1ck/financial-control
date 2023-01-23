package br.edu.ifpi.financialcontrol.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProblemDetails {
    private String title;
    private String detail;
    private Integer status;
    private LocalDateTime timestamp;
    private List<FieldDetails> fields;
}
