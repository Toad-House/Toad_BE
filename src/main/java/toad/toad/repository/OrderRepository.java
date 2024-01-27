package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.Order;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_UserId(int userId);

    List<Order> findByProduct_ProductId(int productId);

    @Query("SELECT o.product.productId, SUM(o.orderNum) FROM Order o WHERE o.product.productId = :productId GROUP BY o.product.productId")
    Optional<Integer> getTotalSalesQuantityByProductId(@Param("productId") int productId);
}
