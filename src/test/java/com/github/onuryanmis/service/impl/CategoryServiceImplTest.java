package com.github.onuryanmis.service.impl;

import com.github.onuryanmis.dto.CategoryDto;
import com.github.onuryanmis.mapper.CategoryMapper;
import com.github.onuryanmis.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("[CategoryService] - findAll() method")
    void testFindAll() {
        CategoryDto categoryDto1 = new CategoryDto("1", "Category 1", "Category 1 description");
        CategoryDto categoryDto2 = new CategoryDto("2", "Category 2", "Category 2 description");

        when(categoryRepository.findAll())
                .thenReturn(Flux.just(CategoryMapper.toEntity(categoryDto1), CategoryMapper.toEntity(categoryDto2)));

        Flux<CategoryDto> result = categoryService.findAll();

        StepVerifier.create(result)
                .expectNext(categoryDto1, categoryDto2)
                .verifyComplete();

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[CategoryService] - findById() method")
    void testFindById() {
        CategoryDto categoryDto = new CategoryDto("1", "Category 1", "Category 1 description");
        when(categoryRepository.findById(anyString()))
                .thenReturn(Mono.just(CategoryMapper.toEntity(categoryDto)));

        Mono<CategoryDto> result = categoryService.findById("1");

        StepVerifier.create(result)
                .expectNext(categoryDto)
                .verifyComplete();

        verify(categoryRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("[CategoryService] - save() method")
    void testSave() {
        CategoryDto categoryDto = new CategoryDto("1", "Category 1", "Category 1 description");
        when(categoryRepository.save(any()))
                .thenReturn(Mono.just(CategoryMapper.toEntity(categoryDto)));

        Mono<CategoryDto> result = categoryService.save(categoryDto);

        StepVerifier.create(result)
                .expectNext(categoryDto)
                .verifyComplete();

        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[CategoryService] - update() method")
    void testUpdate() {
        CategoryDto categoryDto = new CategoryDto("1", "Updated Category", "Updated Description");
        when(categoryRepository.findById(anyString()))
                .thenReturn(Mono.just(CategoryMapper.toEntity(new CategoryDto("1", "Category 1", "Category 1 description"))));

        when(categoryRepository.save(any()))
                .thenReturn(Mono.just(CategoryMapper.toEntity(categoryDto)));

        Mono<CategoryDto> result = categoryService.update("1", categoryDto);

        StepVerifier.create(result)
                .expectNext(categoryDto)
                .verifyComplete();

        verify(categoryRepository, times(1)).findById("1");
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[CategoryService] - delete() method")
    void testDelete() {
        when(categoryRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = categoryService.delete("1");

        StepVerifier.create(result).verifyComplete();

        verify(categoryRepository, times(1)).deleteById("1");
    }
}
