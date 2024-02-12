package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findAll();
    List<Material> findByMaterialNameContaining(String keyword);
    List<Material> findAllByCompanyCompanyId(Integer companyId);
}
