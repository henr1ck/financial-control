package br.edu.ifpi.financialcontrol.exception;

import lombok.Getter;

@Getter
public class TypeNotFoundException extends ResourceNotFoundException {
    public TypeNotFoundException(String message){
        super(message);
    }
}
