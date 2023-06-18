package com.github.onuryanmis.service;

import com.github.onuryanmis.dto.CategoryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<CategoryDto> findAll();

    Mono<CategoryDto> findById(String id);

    Mono<CategoryDto> save(CategoryDto categoryDto);

    Mono<CategoryDto> update(String id, CategoryDto categoryDto);

    Mono<Void> delete(String id);
}
