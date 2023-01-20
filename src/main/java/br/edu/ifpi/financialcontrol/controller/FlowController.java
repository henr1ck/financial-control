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

    @GetMapping(path = "/{flowId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlowResponseBody> findById(@PathVariable Long flowId) {
        Flow flowFound = flowService.findByIdOrThrowBadRequestException(flowId);
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

    @PutMapping(path = "/{flowId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long flowId, @RequestBody FlowRequestBody flow) {
        Flow flowToBeUpdated = convertToDomainObject(flow);
        flowService.update(flowId, flowToBeUpdated);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{flowId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long flowId) {
        flowService.deleteById(flowId);
        return ResponseEntity.noContent().build();
    }

    FlowResponseBody convertToRepresentationObject(Flow flow) {
        return modelMapper.map(flow, FlowResponseBody.class);
    }

    Flow convertToDomainObject(FlowRequestBody flowResponseBody) {
        return modelMapper.map(flowResponseBody, Flow.class);
    }
}
