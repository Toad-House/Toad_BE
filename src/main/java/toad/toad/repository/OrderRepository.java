package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toad.toad.data.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_UserId(int userId);
}
