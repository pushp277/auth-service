package org.sageDelta.auth_service.repositories;

import org.sageDelta.auth_service.entity.ContactDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailsRepository extends JpaRepository<ContactDetailsEntity, Long> {
    boolean existsByEmail(String email);
}
