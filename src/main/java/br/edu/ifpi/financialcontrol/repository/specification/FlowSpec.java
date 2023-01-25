package br.edu.ifpi.financialcontrol.repository.specification;

import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowFilter;
import br.edu.ifpi.financialcontrol.domain.Flow;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class FlowSpec {

    public static Specification<Flow> withFilter(FlowFilter flowFilter){
        return (root, query, builder) -> {
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

            if(flowFilter.getMinDate() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("date"), flowFilter.getMinDate()));
            }

            if(flowFilter.getMaxDate() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("date"), flowFilter.getMaxDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
