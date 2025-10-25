package dev.alexisardaya.productservice.repository;

import dev.alexisardaya.productservice.model.Product;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByNameContainingIgnoreCase(String name);
}
