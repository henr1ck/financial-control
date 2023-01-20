package br.edu.ifpi.financialcontrol.service.impl;

import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.domain.Type;
import br.edu.ifpi.financialcontrol.repository.FlowRepository;
import br.edu.ifpi.financialcontrol.service.CategoryService;
import br.edu.ifpi.financialcontrol.service.FlowService;
import br.edu.ifpi.financialcontrol.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {
    private final FlowRepository flowRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;

    @Override
    public Flow findByIdOrThrowBadRequestException(Long id) {
        return flowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<Flow> findAll() {
        return flowRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Flow save(Flow flow) {
        var categoryId = flow.getCategory().getId();
        Category category = categoryService.findByIdOrThrowBadRequestException(categoryId);

        var typeId = flow.getType().getId();
        Type type = typeService.findByIdOrThrowBadRequestException(typeId);

        flow.setCategory(category);
        flow.setType(type);

        return flowRepository.save(flow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, Flow flow) {
        var categoryId = flow.getCategory().getId();
        categoryService.findByIdOrThrowBadRequestException(categoryId);

        var typeId = flow.getType().getId();
        typeService.findByIdOrThrowBadRequestException(typeId);

        Flow flowToBeUpdated = findByIdOrThrowBadRequestException(id);
        flow.setId(flowToBeUpdated.getId());
        flow.setDate(flowToBeUpdated.getDate());

        flowRepository.save(flow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        flowRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
