package by.pogodkin.onlineshop.repository;

import by.pogodkin.onlineshop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {
    Product findFirstByName(String name);
}