package com.github.onuryanmis.service.impl;

import com.github.onuryanmis.dto.CategoryDto;
import com.github.onuryanmis.mapper.CategoryMapper;
import com.github.onuryanmis.repository.CategoryRepository;
import com.github.onuryanmis.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Flux<CategoryDto> findAll() {
        return categoryRepository
                .findAll()
                .map(CategoryMapper::toDto);
    }

    @Override
    public Mono<CategoryDto> findById(String id) {
        return categoryRepository
                .findById(id)
                .map(CategoryMapper::toDto);
    }

    @Override
    public Mono<CategoryDto> save(CategoryDto categoryDto) {
        return categoryRepository
                .save(CategoryMapper.toEntity(categoryDto))
                .map(CategoryMapper::toDto);
    }

    @Override
    public Mono<CategoryDto> update(String id, CategoryDto categoryDto) {
        return categoryRepository
                .findById(id)
                .flatMap(category -> {
                    category.setName(categoryDto.getName());
                    category.setDescription(categoryDto.getDescription());
                    return categoryRepository.save(category);
                })
                .map(CategoryMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return categoryRepository.deleteById(id);
    }
}
