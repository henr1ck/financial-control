package br.edu.ifpi.financialcontrol.service;

import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowStatisticsFilter;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatisticsWithCategoryName;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatistics;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowType;

import java.util.List;

public interface FlowStatisticsService {
    List<FlowStatistics> calculateFlowGroupByDate(FlowType type, FlowStatisticsFilter statistics, String timeOffSet);
    List<FlowStatisticsWithCategoryName> calculateFlowStatisticsGroupByCategory(FlowType flowType, FlowStatisticsFilter statistics, String timeOffSet);
}
