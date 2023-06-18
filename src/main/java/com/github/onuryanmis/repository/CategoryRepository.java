package com.github.onuryanmis.repository;

import com.github.onuryanmis.entity.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
