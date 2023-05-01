package com.hescha.autochapterstore.service;

import com.hescha.autochapterstore.model.Category;
import com.hescha.autochapterstore.model.Product;
import com.hescha.autochapterstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends CrudService<Product> {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Product> findByCategory(Category category) {
        return repository.findByCategory(category);
    }

    public List<Product> findByNameContainsOrDescriptionContains(String searchPhrase) {
        return repository.findByNameContainingOrDescriptionContaining(searchPhrase, searchPhrase)
                .stream()
                .filter(product -> product.getDeleted() == null || !product.getDeleted())
                .collect(Collectors.toList());
    }


    public Product update(Long id, Product entity) {
        Product read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity Product not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(Product entity, Product read) {
        read.setName(entity.getName());
        read.setDescription(entity.getDescription());
        read.setImage(entity.getImage());
        read.setPrice(entity.getPrice());
        read.setCategory(entity.getCategory());
    }

    public List<Product> readAllNotDeleted() {
        return repository.findAll().stream()
                .filter(product -> product.getDeleted() == null || !product.getDeleted())
                .collect(Collectors.toList());
    }
}
