package br.edu.ifpi.financialcontrol.exception;

import lombok.Getter;

@Getter
public class FlowNotFoundException extends ResourceNotFoundException{
    public FlowNotFoundException(String message){
        super(message);
    }
}
