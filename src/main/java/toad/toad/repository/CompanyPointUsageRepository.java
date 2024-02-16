package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.CompanyPointUsage;

@Repository
public interface CompanyPointUsageRepository extends JpaRepository<CompanyPointUsage, Integer> {
}
