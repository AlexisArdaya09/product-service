package dev.alexisardaya.productservice.service;

import dev.alexisardaya.productservice.dto.ProductRequest;
import dev.alexisardaya.productservice.dto.ProductResponse;
import dev.alexisardaya.productservice.dto.ProductSummaryResponse;
import dev.alexisardaya.productservice.exception.ResourceNotFoundException;
import dev.alexisardaya.productservice.mapper.ProductMapper;
import dev.alexisardaya.productservice.model.Product;
import dev.alexisardaya.productservice.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public List<ProductResponse> findAll(String name) {
    List<Product> products = (name == null || name.isBlank())
        ? repository.findAll()
        : repository.findByNameContainingIgnoreCase(name);
    return products.stream()
        .map(ProductMapper::toResponse)
        .collect(Collectors.toList());
  }

  public List<ProductSummaryResponse> findAllAndSummary() {
    List<Product> products = repository.findAll();
    return products.stream()
        .map(ProductMapper::toSummaryResponse)
        .collect(Collectors.toList());
  }

  public ProductResponse findById(Long id) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
    return ProductMapper.toResponse(product);
  }

  public ProductResponse create(ProductRequest request) {
    Product product = new Product();
    Product saved = repository.save(ProductMapper.toEntity(request, product));
    return ProductMapper.toResponse(saved);
  }

  public ProductResponse update(Long id, ProductRequest request) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
    Product updated = repository.save(ProductMapper.toEntity(request, product));

    return ProductMapper.toResponse(ProductMapper.toEntity(request, updated));
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Producto " + id + " no encontrado");
    }
    repository.deleteById(id);
  }
}