package br.edu.ifpi.financialcontrol.exception;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends ResourceNotFoundException{
    public CategoryNotFoundException(String message){
        super(message);
    }
}
