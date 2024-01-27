package toad.toad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toad.toad.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);
}
