package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping(path = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> findById(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.findByIdOrThrowBadRequestException(categoryId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> save(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping(path = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long categoryId, @RequestBody Category category){
        categoryService.update(categoryId, category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId){
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
