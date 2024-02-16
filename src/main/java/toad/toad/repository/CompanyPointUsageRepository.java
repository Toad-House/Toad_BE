package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.CompanyPointUsage;

import java.util.List;

@Repository
public interface CompanyPointUsageRepository extends JpaRepository<CompanyPointUsage, Integer> {
    List<CompanyPointUsage> findByCompany_CompanyId(int companyId);
}
