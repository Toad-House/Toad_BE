package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toad.toad.data.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findByCompanyName(String companyName);

}
