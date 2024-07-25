package com.SportyShoes.SportyShoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SportyShoes.SportyShoes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
