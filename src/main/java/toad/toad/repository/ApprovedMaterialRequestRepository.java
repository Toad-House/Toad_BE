package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.ApprovedMaterialRequest;

@Repository
public interface ApprovedMaterialRequestRepository extends JpaRepository<ApprovedMaterialRequest, Integer> {
    ApprovedMaterialRequest findByRequestId(Integer requestId);
}
