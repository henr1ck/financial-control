package br.edu.ifpi.financialcontrol.controller.dto.flow;

import lombok.Getter;

@Getter
public enum FlowType {

    INFLOW(1L),
    OUTFLOW(2L);

    private final Long id;

    FlowType(Long id) {
        this.id = id;
    }
    public Long getId(){
        return id;
    }
}
