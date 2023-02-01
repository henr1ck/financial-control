package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowStatisticsFilter;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatistics;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatisticsWithCategoryName;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowType;
import br.edu.ifpi.financialcontrol.service.FlowStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final FlowStatisticsService statisticsService;

    @GetMapping(path = "/inflow")
    public ResponseEntity<List<FlowStatistics>> calculateinFlow(FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatistics> flowStatisticGroupByTypes = statisticsService.calculateFlowGroupByDate(FlowType.INFLOW ,flowStatisticsFilter, timeOffSet);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(flowStatisticGroupByTypes);
    }

    @GetMapping(path = "/outflow")
    public ResponseEntity<List<FlowStatistics>> calculateOutFlow(FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatistics> flowStatisticGroupByTypes = statisticsService.calculateFlowGroupByDate(FlowType.OUTFLOW ,flowStatisticsFilter, timeOffSet);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(flowStatisticGroupByTypes);
    }

    @GetMapping(path = "/inflow-by-category")
    public ResponseEntity<List<FlowStatisticsWithCategoryName>> calculateinFlowByCategory(FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatisticsWithCategoryName> statistics = statisticsService.calculateFlowStatisticsGroupByCategory(FlowType.INFLOW ,flowStatisticsFilter, timeOffSet);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(statistics);
    }

    @GetMapping(path = "/outflow-by-category")
    public ResponseEntity<List<FlowStatisticsWithCategoryName>> calculateOutFlowByCategory(FlowStatisticsFilter flowStatisticsFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        List<FlowStatisticsWithCategoryName> statistics = statisticsService.calculateFlowStatisticsGroupByCategory(FlowType.OUTFLOW ,flowStatisticsFilter, timeOffSet);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(statistics);
    }
}
