package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.type.TypeResponseBody;
import br.edu.ifpi.financialcontrol.domain.Type;
import br.edu.ifpi.financialcontrol.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;
    private final ModelMapper modelMapper;

    @GetMapping(path = "/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeResponseBody> findById(@PathVariable Long typeId){
        Type typeFound = typeService.findByIdOrThrowBadRequestException(typeId);
        TypeResponseBody typeResponseBody = modelMapper.map(typeFound, TypeResponseBody.class);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .body(typeResponseBody);
    }

}
