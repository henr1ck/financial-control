package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowStatisticsFilter;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatistics;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatisticsWithCategoryName;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowType;
import br.edu.ifpi.financialcontrol.service.FlowStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Tag(name = "Statistics")
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final FlowStatisticsService statisticsService;

    @Operation(summary = "Calcula estatísticas básicas sobre o fluxo de entrada",
            description = "Pode receber o intervalo entre datas para filtrar as estatísticas a serem calculadas" +
                    ", e agrupa-las pela data. Os valores retornados são: quantidade, valor total e data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FlowStatistics.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content)
    })
    @GetMapping(path = "/inflow")
    public ResponseEntity<List<FlowStatistics>> calculateinFlow(@Nullable FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatistics> flowStatisticGroupByTypes = statisticsService.calculateFlowGroupByDate(FlowType.INFLOW ,flowStatisticsFilter, timeOffSet);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(flowStatisticGroupByTypes);
    }

    @Operation(summary = "Calcula estatísticas básicas sobre o fluxo de saída",
            description = "Pode receber o intervalo entre datas para filtrar as estatísticas a serem calculadas" +
                    ", e agrupa-las pela data. Os valores retornados são: quantidade, valor total e data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FlowStatistics.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content)
    })
    @GetMapping(path = "/outflow")
    public ResponseEntity<List<FlowStatistics>> calculateOutFlow(@Nullable FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatistics> flowStatisticGroupByTypes = statisticsService.calculateFlowGroupByDate(FlowType.OUTFLOW ,flowStatisticsFilter, timeOffSet);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(flowStatisticGroupByTypes);
    }

    @Operation(summary = "Calcula estatísticas básicas sobre o fluxo de entrada e os agrupam por categorias",
            description = "Pode receber o intervalo entre datas para filtrar as estatísticas a serem calculadas" +
                    ", e agrupa-las por categorias. Os valores retornados são: " +
                    "soma, quantidade, valor total, valor mínimo e valor máximo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FlowStatisticsWithCategoryName.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content)
    })
    @GetMapping(path = "/inflow-by-category")
    public ResponseEntity<List<FlowStatisticsWithCategoryName>> calculateinFlowByCategory(@Nullable FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatisticsWithCategoryName> statistics = statisticsService.calculateFlowStatisticsGroupByCategory(FlowType.INFLOW ,flowStatisticsFilter, timeOffSet);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(statistics);
    }

    @Operation(summary = "Calcula estatísticas básicas sobre o fluxo de saída os agrupam por categorias",
            description = "Pode receber o intervalo entre datas para filtrar as estatísticas a serem calculadas" +
                    ", e agrupa-las por categorias. Os valores retornados são: " +
                    "soma, quantidade, valor total, valor mínimo e valor máximo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FlowStatisticsWithCategoryName.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content)
    })
    @GetMapping(path = "/outflow-by-category")
    public ResponseEntity<List<FlowStatisticsWithCategoryName>> calculateOutFlowByCategory(@Nullable FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatisticsWithCategoryName> statistics = statisticsService.calculateFlowStatisticsGroupByCategory(FlowType.OUTFLOW ,flowStatisticsFilter, timeOffSet);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(statistics);
    }
}
