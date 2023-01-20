package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.domain.Type;
import br.edu.ifpi.financialcontrol.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping(path = "/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Type> findById(@PathVariable Long typeId){
        return ResponseEntity.ok(typeService.findByIdOrThrowBadRequestException(typeId));
    }

}
