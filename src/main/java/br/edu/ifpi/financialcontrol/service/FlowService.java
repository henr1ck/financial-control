package br.edu.ifpi.financialcontrol.service;

import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.repository.specification.FlowSpec;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface FlowService {

    Flow findByCodeOrThrowBadRequestException(String code);

    List<Flow> findAll(Specification<Flow> specification);

    Flow save(Flow flow);

    void update(String code, Flow flow);

    void deleteById(String code);

}
