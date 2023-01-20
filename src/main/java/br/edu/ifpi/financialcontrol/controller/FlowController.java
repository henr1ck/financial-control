package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/flow")
@RequiredArgsConstructor
public class FlowController {

    private final FlowService flowService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flow>> findAll(){
        return ResponseEntity.ok(flowService.findAll());
    }

    @GetMapping(path = "/{flowId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flow> findById(@PathVariable Long flowId){
        return ResponseEntity.ok(flowService.findByIdOrThrowBadRequestException(flowId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flow> save(@RequestBody Flow flow){
        return ResponseEntity.ok(flowService.save(flow));
    }

    @PutMapping(path = "/{flowId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long flowId, @RequestBody Flow flow){
        flowService.update(flowId, flow);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{flowId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long flowId){
        flowService.deleteById(flowId);
        return ResponseEntity.noContent().build();
    }
}
