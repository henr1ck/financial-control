package br.edu.ifpi.financialcontrol.repository.specification;

import br.edu.ifpi.financialcontrol.controller.dto.filter.FlowFilter;
import br.edu.ifpi.financialcontrol.domain.Flow;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class FlowSpec {

    public static Specification<Flow> withFilter(FlowFilter flowFilter){
        return (root, query, builder) -> {
            if(query.getResultType().equals(Flow.class)){
                root.fetch("type", JoinType.INNER);
                root.fetch("category", JoinType.INNER);
            }
            List<Predicate> predicates = new ArrayList<>();

            if(flowFilter.getTypeId() != null){
                predicates.add(builder.equal(root.get("type"), flowFilter.getTypeId()));
            }

            if(flowFilter.getCategoryId() != null){
                predicates.add(builder.equal(root.get("category"), flowFilter.getCategoryId()));
            }

            if(flowFilter.getMinValue() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("value"), flowFilter.getMinValue()));
            }

            if(flowFilter.getMaxValue() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("value"), flowFilter.getMaxValue()));
            }

            if(flowFilter.getInitialDate() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("date"), flowFilter.getInitialDate()));
            }

            if(flowFilter.getFinalDate() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("date"), flowFilter.getFinalDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
