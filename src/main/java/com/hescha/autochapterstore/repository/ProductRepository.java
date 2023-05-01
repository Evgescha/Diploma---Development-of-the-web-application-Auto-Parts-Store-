package com.hescha.autochapterstore.repository;

import com.hescha.autochapterstore.model.Category;
import com.hescha.autochapterstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByNameContainingOrDescriptionContaining(String searchPhrase, String searchPhrase2);
}
