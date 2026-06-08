package com.ilya.webproject.dao;

import com.ilya.webproject.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    boolean save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByName(String name);
    boolean update(Product product);
    boolean deleteById(Long id);
    boolean updateStock(Long id, int quantity);
    boolean incrementStock(Long productId, int quantity);
}