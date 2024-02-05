package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.CompletedMaterialRequest;

@Repository
public interface CompletedMaterialRequestRepository extends JpaRepository<CompletedMaterialRequest, Integer> {
    CompletedMaterialRequest findByRequestId(Integer requestId);
}