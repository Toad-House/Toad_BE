package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.CanceledMaterialRequest;

import java.util.List;

@Repository
public interface CanceledMaterialRequestRepository extends JpaRepository<CanceledMaterialRequest, Integer> {
    CanceledMaterialRequest findByMaterialRequestRequestId(Integer requestId);
    List<CanceledMaterialRequest> findAllByMaterialRequestRequestId(Integer requestId);
    boolean existsByMaterialRequest_RequestId(int requestId);
}
