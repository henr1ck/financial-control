package br.edu.ifpi.financialcontrol.service.impl;

import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.exception.CategoryNotFoundException;
import br.edu.ifpi.financialcontrol.repository.CategoryRepository;
import br.edu.ifpi.financialcontrol.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findByIdOrThrowBadRequestException(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id %d not found", id)));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, Category category) {
        Category categoryToBeReplaced = findByIdOrThrowBadRequestException(id);
        category.setId(categoryToBeReplaced.getId());

        categoryRepository.save(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        categoryRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
