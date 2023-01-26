package br.edu.ifpi.financialcontrol.service.impl;

import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.domain.Flow;
import br.edu.ifpi.financialcontrol.domain.Type;
import br.edu.ifpi.financialcontrol.exception.FlowNotFoundException;
import br.edu.ifpi.financialcontrol.repository.FlowRepository;
import br.edu.ifpi.financialcontrol.repository.specification.FlowSpec;
import br.edu.ifpi.financialcontrol.service.CategoryService;
import br.edu.ifpi.financialcontrol.service.FlowService;
import br.edu.ifpi.financialcontrol.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {
    private final FlowRepository flowRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;

    @Override
    public Flow findByCodeOrThrowBadRequestException(String code) {
        return flowRepository.findByCode(code)
                .orElseThrow(() -> new FlowNotFoundException(String.format("Flow with code %s not found", code)));
    }

    @Override
    public Page<Flow> findAll(Specification<Flow> specification, Pageable pageable) {
        return flowRepository.findAll(specification, pageable);
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
    public void update(String code, Flow flow) {
        var categoryId = flow.getCategory().getId();
        categoryService.findByIdOrThrowBadRequestException(categoryId);

        var typeId = flow.getType().getId();
        typeService.findByIdOrThrowBadRequestException(typeId);

        Flow flowToBeUpdated = findByCodeOrThrowBadRequestException(code);
        flow.setId(flowToBeUpdated.getId());
        flow.setDate(flowToBeUpdated.getDate());

        flow.setCode(flowToBeUpdated.getCode());
        flowRepository.save(flow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String code) {
        flowRepository.delete(findByCodeOrThrowBadRequestException(code));
    }
}
