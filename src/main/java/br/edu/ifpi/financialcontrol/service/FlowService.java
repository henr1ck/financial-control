package br.edu.ifpi.financialcontrol.service;

import br.edu.ifpi.financialcontrol.domain.Flow;

import java.util.List;
import java.util.UUID;

public interface FlowService {

    Flow findByCodeOrThrowBadRequestException(String code);

    List<Flow> findAll();

    Flow save(Flow flow);

    void update(String code, Flow flow);

    void deleteById(String code);

}
