package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Role;
import com.utilityinternational.utility_backend.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    boolean existsByName(ERole name);
}