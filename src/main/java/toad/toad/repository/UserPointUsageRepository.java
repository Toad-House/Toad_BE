package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toad.toad.data.entity.UserPointUsage;

@Repository
public interface UserPointUsageRepository extends JpaRepository<UserPointUsage, Integer> {
}
