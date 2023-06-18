package com.github.onuryanmis.controller;

import com.github.onuryanmis.dto.CategoryDto;
import com.github.onuryanmis.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    @DisplayName("GET /api/categories")
    public void testIndex() {
        CategoryDto category1 = new CategoryDto("1", "Category 1", "Description 1");
        CategoryDto category2 = new CategoryDto("2", "Category 2", "Description 2");
        Flux<CategoryDto> categoryFlux = Flux.just(category1, category2);

        when(categoryService.findAll()).thenReturn(categoryFlux);

        webTestClient.get()
                .uri("/api/categories")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CategoryDto.class)
                .hasSize(2)
                .contains(category1, category2);

        verify(categoryService, times(1)).findAll();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("GET /api/categories/{id}")
    public void testShow() {
        String categoryId = "1";
        CategoryDto category = new CategoryDto(categoryId, "Category 1", "Description 1");

        when(categoryService.findById(categoryId)).thenReturn(Mono.just(category));

        webTestClient.get()
                .uri("/api/categories/{id}", categoryId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryDto.class)
                .isEqualTo(category);

        verify(categoryService, times(1)).findById(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("POST /api/categories")
    public void testStore() {
        CategoryDto category = new CategoryDto("1", "Category 1", "Description 1");

        when(categoryService.save(any(CategoryDto.class))).thenReturn(Mono.just(category));

        webTestClient.post()
                .uri("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CategoryDto.class)
                .isEqualTo(category);

        verify(categoryService, times(1)).save(any(CategoryDto.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("PUT /api/categories/{id}")
    public void testUpdate() {
        String categoryId = "1";
        CategoryDto category = new CategoryDto(categoryId, "Category 1", "Description 1");

        when(categoryService.update(eq(categoryId), any(CategoryDto.class))).thenReturn(Mono.just(category));

        webTestClient.put()
                .uri("/api/categories/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryDto.class)
                .isEqualTo(category);

        verify(categoryService, times(1)).update(eq(categoryId), any(CategoryDto.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("DELETE /api/categories/{id}")
    public void testDelete() {
        String categoryId = "1";

        when(categoryService.delete(categoryId)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/categories/{id}", categoryId)
                .exchange()
                .expectStatus().isNoContent();

        verify(categoryService, times(1)).delete(categoryId);
        verifyNoMoreInteractions(categoryService);
    }
}
