package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowStatisticsFilter;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatistics;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatisticsWithCategoryName;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowType;
import br.edu.ifpi.financialcontrol.service.FlowStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final FlowStatisticsService statisticsService;

    @GetMapping(path = "/inflow")
    public ResponseEntity<List<FlowStatistics>> calculateinFlow(FlowStatisticsFilter flowStatisticsFilter) {
        List<FlowStatistics> flowStatisticGroupByTypes = statisticsService.calculateFlowGroupByDate(FlowType.INFLOW ,flowStatisticsFilter);

        return ResponseEntity.ok(flowStatisticGroupByTypes);
    }

    @GetMapping(path = "/outflow")
    public ResponseEntity<List<FlowStatistics>> calculateOutFlow(FlowStatisticsFilter flowStatisticsFilter) {
        List<FlowStatistics> flowStatisticGroupByTypes = statisticsService.calculateFlowGroupByDate(FlowType.OUTFLOW ,flowStatisticsFilter);

        return ResponseEntity.ok(flowStatisticGroupByTypes);
    }

    @GetMapping(path = "/inflow-by-category")
    public ResponseEntity<List<FlowStatisticsWithCategoryName>> calculateinFlowByCategory(FlowStatisticsFilter flowStatisticsFilter) {
        List<FlowStatisticsWithCategoryName> statistics = statisticsService.calculateFlowStatisticsGroupByCategory(FlowType.INFLOW ,flowStatisticsFilter);

        return ResponseEntity.ok(statistics);
    }

    @GetMapping(path = "/outflow-by-category")
    public ResponseEntity<List<FlowStatisticsWithCategoryName>> calculateOutFlowByCategory(FlowStatisticsFilter flowStatisticsFilter) {
        List<FlowStatisticsWithCategoryName> statistics = statisticsService.calculateFlowStatisticsGroupByCategory(FlowType.OUTFLOW ,flowStatisticsFilter);
        return ResponseEntity.ok(statistics);
    }
}
