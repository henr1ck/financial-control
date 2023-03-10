package br.edu.ifpi.financialcontrol.controller;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryResponseBody;
import br.edu.ifpi.financialcontrol.domain.Category;
import br.edu.ifpi.financialcontrol.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Tag(name = "Category")
@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Encontra todas as categorias", description = "Busca e retorna todas as categorias disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                            schema = @Schema(implementation = CategoryResponseBody.class))),
    })
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

    @Operation(summary = "Encontra uma categoria pelo ID", description = "Busca e retorna uma categoria de acordo com o ID especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(implementation = CategoryResponseBody.class))),

            @ApiResponse(responseCode = "400", description = "Category not found!", content = @Content),
    })
    @GetMapping(path = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseBody> findById(@PathVariable Long categoryId){
        Category category = categoryService.findByIdOrThrowBadRequestException(categoryId);
        CategoryResponseBody categoryResponseBody = convertToRepresentationObject(category);

        return ResponseEntity.ok(categoryResponseBody);
    }

    @Operation(summary = "Salva uma nova categoria.",
            description = "Recebe uma categoria no corpo da requisição e a salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(implementation = CategoryResponseBody.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseBody> save(@Valid @RequestBody CategoryRequestBody category){
        Category categoryToBeSaved = convertToDomainObject(category);
        Category categorySaved = categoryService.save(categoryToBeSaved);
        CategoryResponseBody categoryResponseBody = convertToRepresentationObject(categorySaved);

        return ResponseEntity.ok(categoryResponseBody);
    }

    @Operation(summary = "Atualiza uma categoria existente.",
            description = "Recebe o ID na URL da categoria a ser atualizada, e os novos dados no corpo da solitação." +
                    "Retorna o status NO CONTENT se a operação for bem sucedida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category has been updated", content = @Content),

            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content),
    })
    @PutMapping(path = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@ParameterObject @PathVariable Long categoryId, @Valid @RequestBody CategoryRequestBody category){
        Category categoryToBeUpdated = convertToDomainObject(category);
        categoryService.update(categoryId, categoryToBeUpdated);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Exclui uma categoria.",
            description = "Recebe o ID na URL da categoria a ser excluida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category has been deleted", content = @Content),

            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content),
    })
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
