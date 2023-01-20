package br.edu.ifpi.financialcontrol.service;

import br.edu.ifpi.financialcontrol.domain.Category;

import java.util.List;

public interface CategoryService {

    Category findByIdOrThrowBadRequestException(Long id);

    List<Category> findAll();

    Category save(Category category);

    void update(Long id, Category category);

    void deleteById(Long id);

}
