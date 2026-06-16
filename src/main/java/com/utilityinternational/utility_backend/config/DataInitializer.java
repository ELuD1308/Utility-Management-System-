package com.utilityinternational.utility_backend.config;

import com.utilityinternational.utility_backend.entity.Role;
import com.utilityinternational.utility_backend.enums.ERole;
import com.utilityinternational.utility_backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    // Spring automatically autowires this constructor dependency
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Use your existsByName method to check for ROLE_CUSTOMER
        if (!roleRepository.existsByName(ERole.ROLE_CUSTOMER)) {
            Role customerRole = new Role();
            customerRole.setName(ERole.ROLE_CUSTOMER);
            roleRepository.save(customerRole);
            System.out.println("--> Seeded database row: ROLE_CUSTOMER");
        }

        // Use your existsByName method to check for ROLE_CALL_CENTER_AGENT
        if (!roleRepository.existsByName(ERole.ROLE_CALL_CENTER_AGENT)) {
            Role agentRole = new Role();
            agentRole.setName(ERole.ROLE_CALL_CENTER_AGENT);
            roleRepository.save(agentRole);
            System.out.println("--> Seeded database row: ROLE_CALL_CENTER_AGENT");
        }
    }
}