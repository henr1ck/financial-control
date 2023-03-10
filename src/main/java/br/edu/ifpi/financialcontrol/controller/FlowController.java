package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.filter.CustomFlowViewPage;
import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowFilter;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowResponseBody;
import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.repository.specification.FlowSpec;
import br.edu.ifpi.financialcontrol.service.FlowService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Tag(name = "Flow")
@RestController
@RequestMapping(path = "/flow")
@RequiredArgsConstructor
public class FlowController {
    private final FlowService flowService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Consulta por páginas de fluxos",
            description = "Retorna uma página contendo fluxos que podem ser filtrados de acordo " +
                    "com os parâmetros especificados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Failed to convert one or more parameter values",
                    content = @Content
            ),
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CustomFlowViewPage.class))
            )}
    )
    @JsonView(FlowView.Simple.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomFlowViewPage> findAll(@ParameterObject FlowFilter flowFilter, @Parameter(hidden = true) Pageable pageable) throws ClassNotFoundException {
        Page<Flow> flowPage = flowService.findAll(FlowSpec.withFilter(flowFilter), pageable);
        List<FlowResponseBody> flowResponseBodies = flowPage.getContent().stream()
                .map(this::convertToRepresentationObject)
                .collect(Collectors.toList());

        CustomFlowViewPage customFlowViewPage = new CustomFlowViewPage(flowResponseBodies, pageable, flowPage.getTotalElements());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(customFlowViewPage);
    }

    @Operation(
            summary = "Consulta por um fluxo específico",
            description = "Retorna um único fluxo de acordo com o código universal (UUID) específicado"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Flow Response Body",
                            content = @Content(schema = @Schema(implementation = FlowResponseBody.class))),
                    @ApiResponse(responseCode = "400", description = "Flow not found!", content = @Content)
            }
    )
    @GetMapping(path = "/{flowCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlowResponseBody> findById(@ParameterObject @PathVariable String flowCode) {
        Flow flowFound = flowService.findByCodeOrThrowBadRequestException(flowCode);
        FlowResponseBody flowResponseBody = convertToRepresentationObject(flowFound);

        return ResponseEntity.ok(flowResponseBody);
    }

    @Operation(
            summary = "Salva um fluxo",
            description = "Recebe um fluxo no corpo da solicitação, e o retorna com o ID gerado."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Flow Saved Response Body",
                            content = @Content(schema = @Schema(implementation = FlowResponseBody.class))),
                    @ApiResponse(responseCode = "400", description = "Request body message isn't valid!", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<FlowResponseBody> save(
            @RequestBody
            @Valid
            FlowRequestBody flow
    ) {

        Flow flowToBeSaved = convertToDomainObject(flow);
        Flow flowSaved = flowService.save(flowToBeSaved);
        FlowResponseBody flowResponseBody = convertToRepresentationObject(flowSaved);

        return ResponseEntity.ok(flowResponseBody);
    }

    @Operation(
            summary = "Atualiza um fluxo",
            description = "Recebe um código como parâmetro, e o fluxo a ser atualizado no corpo da solicitação. " +
                    "Retorna o código de status OK se bem sucedido."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Flow has been updated",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Request body message isn't valid!", content = @Content)
            }
    )
    @PutMapping(path = "/{flowCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@ParameterObject @PathVariable String flowCode, @Valid @RequestBody FlowRequestBody flow) {
        Flow flowToBeUpdated = convertToDomainObject(flow);
        flowService.update(flowCode, flowToBeUpdated);

        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Exclui um fluxo",
            description = "Recebe um código de fluxo como parâmetro e o exclui. " +
                    "Retorna o código de status NO CONTENT se bem sucedido."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Flow has been deleted",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Flow with code {?} not found", content = @Content)
            }
    )
    @DeleteMapping(path = "/{flowCode}")
    public ResponseEntity<Void> deleteById(@ParameterObject @PathVariable String flowCode) {
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
