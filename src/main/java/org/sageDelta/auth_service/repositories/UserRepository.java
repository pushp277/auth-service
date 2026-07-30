package org.sageDelta.auth_service.repositories;

import org.sageDelta.auth_service.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    boolean existsByUsername(String username);
    UsersEntity findByUsername(String username);
}
