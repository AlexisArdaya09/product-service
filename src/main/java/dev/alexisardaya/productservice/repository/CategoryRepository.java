package dev.alexisardaya.productservice.repository;

import dev.alexisardaya.productservice.model.Category;
import dev.alexisardaya.productservice.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(String name);

  List<Category> findByNameContainingIgnoreCase(String name);

  boolean existsByName(String name);
}

