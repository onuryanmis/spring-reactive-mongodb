package com.github.onuryanmis.repository;

import com.github.onuryanmis.entity.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@SpringJUnitConfig
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        categoryRepository.deleteAll().block();
    }

    @AfterEach
    public void tearDown() {
        categoryRepository.deleteAll().block();
    }

    @Test
    @DisplayName("[CategoryRepository] - save() method")
    public void testSave() {
        Category category = new Category("1", "Category 1", "Category 1 description");

        Mono<Category> savedCategoryMono = categoryRepository.save(category);

        StepVerifier.create(savedCategoryMono)
                .expectNextMatches(savedCategory -> savedCategory.getId().equals(category.getId()))
                .verifyComplete();
    }

    @Test
    @DisplayName("[CategoryRepository] - findAll() method")
    public void testFindAll() {
        Category category1 = new Category("1", "Category 1", "Category 1 description");
        Category category2 = new Category("2", "Category 2", "Category 2 description");

        Flux<Category> savedCategoriesFlux = categoryRepository.saveAll(Flux.just(category1, category2))
                .thenMany(categoryRepository.findAll());

        StepVerifier.create(savedCategoriesFlux)
                .expectNext(category1, category2)
                .verifyComplete();
    }

    @Test
    @DisplayName("[CategoryRepository] - findById() method")
    public void testFindById() {
        Category category = new Category("1", "Category 1", "Category 1 description");

        Mono<Category> savedCategoryMono = categoryRepository.save(category)
                .then(categoryRepository.findById(category.getId()));

        StepVerifier.create(savedCategoryMono)
                .expectNext(category)
                .verifyComplete();
    }

    @Test
    @DisplayName("[CategoryRepository] - deleteById() method")
    public void testDelete() {
        Category category = new Category("1", "Category 1", "Category 1 description");

        Mono<Void> deletedCategoryMono = categoryRepository.save(category)
                .then(categoryRepository.deleteById(category.getId()));

        StepVerifier.create(deletedCategoryMono)
                .verifyComplete();
    }

    @Test
    @DisplayName("[CategoryRepository] - update() method")
    public void testUpdate() {
        Category category = new Category("1", "Category 1", "Category 1 description");
        String updatedName = "Updated Category 1";

        Mono<Category> updatedCategoryMono = categoryRepository.save(category)
                .flatMap(savedCategory -> {
                    savedCategory.setName(updatedName);
                    return categoryRepository.save(savedCategory);
                });

        StepVerifier.create(updatedCategoryMono)
                .expectNextMatches(updatedCategory -> updatedCategory.getName().equals(updatedName))
                .verifyComplete();
    }
}
