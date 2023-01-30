package br.edu.ifpi.financialcontrol.service.impl;

import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowStatisticsFilter;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatistics;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowStatisticsWithCategoryName;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowType;
import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.domain.Type;
import br.edu.ifpi.financialcontrol.service.FlowStatisticsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class FlowStatisticsServiceImpl implements FlowStatisticsService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<FlowStatistics> calculateFlowGroupByDate(FlowType flowType, FlowStatisticsFilter filter, String timeOffSet) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(FlowStatistics.class);
        var root = query.from(Flow.class);
        var predicates = new ArrayList<Predicate>();

        log.info("TimeOffSet Parameter: {}", ZoneOffset.of(timeOffSet).toString());
        Expression<Date> convertTzFunction = builder.function("convert_tz", Date.class, root.get("date"),
                builder.literal("+00:00"), builder.literal(timeOffSet));

        Expression<Date> dateFunction = builder.function("date", Date.class, convertTzFunction);

        Join<Flow, Type> flowTypeJoin = root.join("type");
        predicates.add(builder.equal(flowTypeJoin.get("id"), flowType.getId()));

        if (filter.getInitialDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(dateFunction, filter.getInitialDate()));
        }

        if (filter.getFinalDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(dateFunction, filter.getFinalDate()));
        }

        CompoundSelection<FlowStatistics> compoundSelection = builder.construct(FlowStatistics.class,
                builder.count(root.get("id")),
                builder.sum(root.get("value")),
                dateFunction
        );

        query.select(compoundSelection).where(predicates.toArray(new Predicate[0])).groupBy(dateFunction);
        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<FlowStatisticsWithCategoryName> calculateFlowStatisticsGroupByCategory(FlowType flowType, FlowStatisticsFilter filter, String timeOffSet) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(FlowStatisticsWithCategoryName.class);
        var root = query.from(Flow.class);
        var predicates = new ArrayList<Predicate>();

        Join<Flow, Category> flowCategoryJoin = root.join("category");
        Join<Flow, Type> flowTypeJoin = root.join("type");

        predicates.add(builder.equal(flowCategoryJoin.get("id"), query.from(Category.class).get("id")));
        predicates.add(builder.equal(flowTypeJoin.get("id"), flowType.getId()));

        log.info("TimeOffSet Parameter: {}", ZoneOffset.of(timeOffSet).toString());
        Expression<Date> convertTzFunction = builder.function("convert_tz", Date.class,
                root.get("date"), builder.literal("+00:00"), builder.literal(timeOffSet));

        Expression<Date> dateFunction = builder.function("date", Date.class, convertTzFunction);

        if(filter.getInitialDate() != null){
            predicates.add(builder.greaterThanOrEqualTo(dateFunction, filter.getInitialDate()));
        }

        if(filter.getFinalDate() != null){
            predicates.add(builder.lessThanOrEqualTo(dateFunction, filter.getFinalDate()));
        }

        Path<BigDecimal> valueAttribute = root.get("value");
        var construct = builder.construct(FlowStatisticsWithCategoryName.class,
                flowCategoryJoin.get("name"),
                builder.count(root.get("id")),
                builder.sum(valueAttribute),
                builder.avg(valueAttribute),
                builder.min(valueAttribute),
                builder.max(valueAttribute)
        );

        query.select(construct).where(predicates.toArray(new Predicate[0])).groupBy(flowCategoryJoin.get("name"));
        return manager.createQuery(query).getResultList();
    }
}
