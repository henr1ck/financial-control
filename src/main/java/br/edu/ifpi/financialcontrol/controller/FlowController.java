package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowResponseBody;
import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/flow")
@RequiredArgsConstructor
public class FlowController {

    private final FlowService flowService;
    private final ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FlowResponseBody>> findAll() {
        List<Flow> flows = flowService.findAll();
        List<FlowResponseBody> flowResponseBodies = flows.stream()
                .map(this::convertToRepresentationObject)
                .collect(Collectors.toList());

        return ResponseEntity.ok(flowResponseBodies);
    }

    @GetMapping(path = "/{flowCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlowResponseBody> findById(@PathVariable String flowCode) {
        Flow flowFound = flowService.findByCodeOrThrowBadRequestException(flowCode);
        FlowResponseBody flowResponseBody = convertToRepresentationObject(flowFound);

        return ResponseEntity.ok(flowResponseBody);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlowResponseBody> save(@RequestBody FlowRequestBody flow) {
        Flow flowToBeSaved = convertToDomainObject(flow);
        Flow flowSaved = flowService.save(flowToBeSaved);
        FlowResponseBody flowResponseBody = convertToRepresentationObject(flowSaved);

        return ResponseEntity.ok(flowResponseBody);
    }

    @PutMapping(path = "/{flowCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable String flowCode, @RequestBody FlowRequestBody flow) {
        Flow flowToBeUpdated = convertToDomainObject(flow);
        flowService.update(flowCode, flowToBeUpdated);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{flowCode}")
    public ResponseEntity<Void> deleteById(@PathVariable String flowCode) {
        flowService.deleteById(flowCode);
        return ResponseEntity.noContent().build();
    }

    FlowResponseBody convertToRepresentationObject(Flow flow) {
        return modelMapper.map(flow, FlowResponseBody.class);
    }

    Flow convertToDomainObject(FlowRequestBody flowResponseBody) {
        return modelMapper.map(flowResponseBody, Flow.class);
    }
}
