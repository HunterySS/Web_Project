package com.ilya.webproject.dao.impl;

import com.ilya.webproject.dao.ProductDao;
import com.ilya.webproject.model.Product;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoImplTest {

    private static ProductDao productDao;
    private static Long testProductId;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
    }

    @Test
    @Order(1)
    void save_ShouldReturnTrue_WhenProductIsValid() {
        Product product = new Product("Test Product", "Test Description", new BigDecimal("99.99"), 10);

        boolean result = productDao.save(product);

        assertTrue(result);
        assertNotNull(product.getId());
        testProductId = product.getId();
    }

    @Test
    @Order(2)
    void findById_ShouldReturnProduct_WhenExists() {
        Optional<Product> product = productDao.findById(testProductId);

        assertTrue(product.isPresent());
        assertEquals("Test Product", product.get().getName());
        assertEquals(new BigDecimal("99.99"), product.get().getPrice());
    }

    @Test
    @Order(3)
    void findAll_ShouldReturnListOfProducts() {
        List<Product> products = productDao.findAll();

        assertNotNull(products);
        assertTrue(products.size() >= 1);
    }

    @Test
    @Order(4)
    void update_ShouldReturnTrue_WhenProductExists() {
        Optional<Product> productOpt = productDao.findById(testProductId);
        assertTrue(productOpt.isPresent());

        Product product = productOpt.get();
        product.setName("Updated Product");
        product.setPrice(new BigDecimal("149.99"));

        boolean result = productDao.update(product);

        assertTrue(result);

        Optional<Product> updated = productDao.findById(testProductId);
        assertTrue(updated.isPresent());
        assertEquals("Updated Product", updated.get().getName());
        assertEquals(new BigDecimal("149.99"), updated.get().getPrice());
    }

    @Test
    @Order(5)
    void updateStock_ShouldReturnTrue_WhenStockIsSufficient() {
        boolean result = productDao.updateStock(testProductId, 5);

        assertTrue(result);

        Optional<Product> product = productDao.findById(testProductId);
        assertTrue(product.isPresent());
        assertEquals(5, product.get().getStock());
    }

    @Test
    @Order(6)
    void updateStock_ShouldReturnFalse_WhenStockIsInsufficient() {
        boolean result = productDao.updateStock(testProductId, 100);

        assertFalse(result);
    }

    @Test
    @Order(7)
    void deleteById_ShouldReturnTrue_WhenProductExists() {
        boolean result = productDao.deleteById(testProductId);

        assertTrue(result);

        Optional<Product> deleted = productDao.findById(testProductId);
        assertTrue(deleted.isEmpty());
    }

    @Test
    void findByName_ShouldReturnProducts() {
        List<Product> products = productDao.findByName("Laptop");

        assertNotNull(products);
        assertTrue(products.stream().anyMatch(p -> p.getName().contains("Laptop")));
    }
}