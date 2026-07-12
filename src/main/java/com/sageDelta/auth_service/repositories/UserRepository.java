package com.sageDelta.auth_service.repositories;

import com.sageDelta.auth_service.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    boolean existsByUsername(String username);

    UsersEntity findByUsername(String username);
}
