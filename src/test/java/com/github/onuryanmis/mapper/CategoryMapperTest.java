package com.github.onuryanmis.mapper;

import com.github.onuryanmis.dto.CategoryDto;
import com.github.onuryanmis.entity.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    public void testToDto() {
        Category category = new Category("id", "name", "description");
        CategoryDto categoryDto = CategoryMapper.toDto(category);

        assertEquals(category.getId(), categoryDto.getId());
        assertEquals(category.getName(), categoryDto.getName());
        assertEquals(category.getDescription(), "test");
    }

    @Test
    public void testToEntity() {
        CategoryDto categoryDto = new CategoryDto("id", "name", "description");
        Category category = CategoryMapper.toEntity(categoryDto);

        assertEquals(categoryDto.getId(), category.getId());
        assertEquals(categoryDto.getName(), category.getName());
        assertEquals(categoryDto.getDescription(), category.getDescription());
    }
}