package dev.alexisardaya.productservice.service;

import dev.alexisardaya.productservice.dto.CategoryRequest;
import dev.alexisardaya.productservice.dto.CategoryResponse;
import dev.alexisardaya.productservice.dto.ProductSummaryResponse;
import dev.alexisardaya.productservice.exception.DuplicateResourceException;
import dev.alexisardaya.productservice.exception.OperationNotAllowedException;
import dev.alexisardaya.productservice.exception.ResourceNotFoundException;
import dev.alexisardaya.productservice.mapper.CategoryMapper;
import dev.alexisardaya.productservice.model.Category;
import dev.alexisardaya.productservice.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private final CategoryRepository repository;

  public CategoryService(CategoryRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<CategoryResponse> findAll() {
    return repository.findAll().stream()
        .map(CategoryMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CategoryResponse findById(Long id) {
    Category category = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoría " + id + " no encontrada"));
    return CategoryMapper.toResponse(category);
  }

  @Transactional(readOnly = true)
  public List<ProductSummaryResponse> findProductsByCategoryId(Long categoryId) {
    Category category = repository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Categoría " + categoryId + " no encontrada"));
    
    return category.getProducts().stream()
        .map(product -> new ProductSummaryResponse(
            product.getId(),
            product.getName(),
            product.getPrice()
        ))
        .collect(Collectors.toList());
  }

  @Transactional
  public CategoryResponse create(CategoryRequest request) {
    if (repository.existsByName(request.name())) {
      throw new DuplicateResourceException(
          "Ya existe una categoría con el nombre: " + request.name());
    }
    Category category = new Category();
    Category saved = repository.save(CategoryMapper.toEntity(request, category));
    return CategoryMapper.toResponse(saved);
  }

  @Transactional
  public CategoryResponse update(Long id, CategoryRequest request) {
    Category category = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoría " + id + " no encontrada"));
    
    // Verificar si el nuevo nombre ya existe en otra categoría
    repository.findByName(request.name())
        .ifPresent(existingCategory -> {
          if (!existingCategory.getId().equals(id)) {
            throw new DuplicateResourceException(
                "Ya existe otra categoría con el nombre: " + request.name());
          }
        });
    
    Category updated = repository.save(CategoryMapper.toEntity(request, category));
    return CategoryMapper.toResponse(updated);
  }

  @Transactional
  public void delete(Long id) {
    Category category = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoría " + id + " no encontrada"));
    
    if (!category.getProducts().isEmpty()) {
      int productCount = category.getProducts().size();
      throw new OperationNotAllowedException(
          String.format(
              "No se puede eliminar la categoría '%s' porque tiene %d producto(s) asociado(s). " +
              "Elimine primero los productos asociados o desasócielos de esta categoría.",
              category.getName(),
              productCount
          ));
    }
    
    repository.deleteById(id);
  }
}

