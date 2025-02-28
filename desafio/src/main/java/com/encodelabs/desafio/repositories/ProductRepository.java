package com.encodelabs.desafio.repositories;

import com.encodelabs.desafio.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(Integer id);
    Optional<Product> findByName(String name);
}
