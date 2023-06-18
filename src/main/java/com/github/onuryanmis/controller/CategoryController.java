package com.github.onuryanmis.controller;

import com.github.onuryanmis.dto.CategoryDto;
import com.github.onuryanmis.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    public Flux<CategoryDto> index() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<CategoryDto> show(@PathVariable String id) {
        return categoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<CategoryDto> store(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PutMapping("/{id}")
    public Mono<CategoryDto> update(@PathVariable String id, @RequestBody CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return categoryService.delete(id);
    }
}
