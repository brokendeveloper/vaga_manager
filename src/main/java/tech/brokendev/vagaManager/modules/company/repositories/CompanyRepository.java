package tech.brokendev.vagaManager.modules.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brokendev.vagaManager.modules.company.entities.CompanyEntity;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity>findByUsernameOrEmail(String username, String email);
}
