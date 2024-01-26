package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductNameContaining(String keyword);

    List<Product> findByProductDescContaining(String keyword);

//    List<Product> findByCompany_CompanyId(int companyId);
}
