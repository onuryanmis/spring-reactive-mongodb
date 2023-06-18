package com.github.onuryanmis.mapper;

import com.github.onuryanmis.dto.CategoryDto;
import com.github.onuryanmis.entity.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }

    public static Category toEntity(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
    }
}
