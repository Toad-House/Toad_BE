package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toad.toad.data.entity.MaterialRequest;

public interface MaterialRequestRepository extends JpaRepository<MaterialRequest, Integer> {
}
