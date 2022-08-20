package by.pogodkin.onlineshop.repository;

import by.pogodkin.onlineshop.model.Order;
import by.pogodkin.onlineshop.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
}
