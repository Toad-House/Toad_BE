package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.CompletedMaterialRequest;

import java.util.List;

@Repository
public interface CompletedMaterialRequestRepository extends JpaRepository<CompletedMaterialRequest, Integer> {
    CompletedMaterialRequest findByMaterialRequestRequestId(Integer requestId);
    List<CompletedMaterialRequest> findAllByMaterialRequestRequestId(Integer requestId);
    boolean existsByMaterialRequest_RequestId(int requestId);

}
