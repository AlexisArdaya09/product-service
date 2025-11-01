package dev.alexisardaya.productservice.mapper;

import dev.alexisardaya.productservice.dto.CategoryRequest;
import dev.alexisardaya.productservice.dto.CategoryResponse;
import dev.alexisardaya.productservice.model.Category;

public final class CategoryMapper {

  private CategoryMapper() {
  }

  public static CategoryResponse toResponse(Category category) {
    return new CategoryResponse(
        category.getId(),
        category.getName(),
        category.getDescription(),
        category.getCreatedAt(),
        category.getUpdatedAt()
    );
  }

  public static Category toEntity(CategoryRequest request, Category entity) {
    entity.setName(request.name());
    entity.setDescription(request.description());
    return entity;
  }
}

