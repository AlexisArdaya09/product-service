package dev.alexisardaya.productservice.mapper;

import dev.alexisardaya.productservice.dto.CategorySummaryResponse;
import dev.alexisardaya.productservice.dto.ProductRequest;
import dev.alexisardaya.productservice.dto.ProductResponse;
import dev.alexisardaya.productservice.dto.ProductSummaryResponse;
import dev.alexisardaya.productservice.model.Category;
import dev.alexisardaya.productservice.model.Product;
import java.util.List;
import java.util.stream.Collectors;

public final class ProductMapper {

  private ProductMapper() {
  }

  public static ProductResponse toResponse(Product product) {
    List<CategorySummaryResponse> categoriesResponse = product.getCategories().stream()
        .map(category -> new CategorySummaryResponse(
            category.getId(),
            category.getName()
        ))
        .collect(Collectors.toList());

    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStock(),
        categoriesResponse,
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }

  public static Product toEntity(ProductRequest request, Product entity, List<Category> categories) {
    entity.setName(request.name());
    entity.setDescription(request.description());
    entity.setPrice(request.price());
    entity.setStock(request.stock());
    entity.setCategories(categories);
    return entity;
  }

  public static ProductSummaryResponse toSummaryResponse(Product product) {
    return new ProductSummaryResponse(
        product.getId(),
        product.getName(),
        product.getPrice()
    );
  }
}