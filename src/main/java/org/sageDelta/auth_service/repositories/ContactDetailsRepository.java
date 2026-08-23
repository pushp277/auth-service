package org.sageDelta.auth_service.repositories;

import org.sageDelta.auth_service.entity.ContactDetailsEntity;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactDetailsRepository extends JpaRepository<ContactDetailsEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UsersEntity> findByEmail(String email);
}
