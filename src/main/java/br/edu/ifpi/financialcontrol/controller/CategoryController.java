package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryResponseBody;
import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryResponseBody>> findAll(){
        List<Category> categories = categoryService.findAll();
        List<CategoryResponseBody> categoryResponseBodies = categories.stream()
                .map(this::convertToRepresentationObject)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(categoryResponseBodies);
    }

    @GetMapping(path = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseBody> findById(@PathVariable Long categoryId){
        Category category = categoryService.findByIdOrThrowBadRequestException(categoryId);
        CategoryResponseBody categoryResponseBody = convertToRepresentationObject(category);

        return ResponseEntity.ok(categoryResponseBody);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseBody> save(@Valid @RequestBody CategoryRequestBody category){
        Category categoryToBeSaved = convertToDomainObject(category);
        Category categorySaved = categoryService.save(categoryToBeSaved);
        CategoryResponseBody categoryResponseBody = convertToRepresentationObject(categorySaved);

        return ResponseEntity.ok(categoryResponseBody);
    }

    @PutMapping(path = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestBody category){
        Category categoryToBeUpdated = convertToDomainObject(category);
        categoryService.update(categoryId, categoryToBeUpdated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId){
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

    CategoryResponseBody convertToRepresentationObject(Category category){
        return modelMapper.map(category, CategoryResponseBody.class);
    }

    Category convertToDomainObject(CategoryRequestBody categoryRequestBody){
        return modelMapper.map(categoryRequestBody, Category.class);
    }
}
