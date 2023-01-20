package br.edu.ifpi.financialcontrol.service;

import br.edu.ifpi.financialcontrol.domain.Type;

public interface TypeService {

    Type findByIdOrThrowBadRequestException(Long id);

}
