package br.edu.ifpi.financialcontrol.service;

import br.edu.ifpi.financialcontrol.domain.Flow;

import java.util.List;

public interface FlowService {

    Flow findByIdOrThrowBadRequestException(Long id);

    List<Flow> findAll();

    Flow save(Flow flow);

    void update(Long id, Flow flow);

    void deleteById(Long id);

}
