package dev.alexisardaya.productservice.service;

import dev.alexisardaya.productservice.dto.CategoryRequest;
import dev.alexisardaya.productservice.dto.CategoryResponse;
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

  @Transactional
  public CategoryResponse create(CategoryRequest request) {
    Category category = new Category();
    Category saved = repository.save(CategoryMapper.toEntity(request, category));
    return CategoryMapper.toResponse(saved);
  }

  @Transactional
  public CategoryResponse update(Long id, CategoryRequest request) {
    Category category = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoría " + id + " no encontrada"));
    Category updated = repository.save(CategoryMapper.toEntity(request, category));
    return CategoryMapper.toResponse(updated);
  }

  @Transactional
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Categoría " + id + " no encontrada");
    }
    repository.deleteById(id);
  }
}

