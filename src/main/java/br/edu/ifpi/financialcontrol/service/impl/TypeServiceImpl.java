package br.edu.ifpi.financialcontrol.service.impl;

import br.edu.ifpi.financialcontrol.domain.Type;
import br.edu.ifpi.financialcontrol.exception.TypeNotFoundException;
import br.edu.ifpi.financialcontrol.repository.TypeRepository;
import br.edu.ifpi.financialcontrol.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    @Override
    public Type findByIdOrThrowBadRequestException(Long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(String.format("Type with id %s not found", id)));
    }
}
