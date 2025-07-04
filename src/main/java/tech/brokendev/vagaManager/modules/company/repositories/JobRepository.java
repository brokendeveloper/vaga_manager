package tech.brokendev.vagaManager.modules.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brokendev.vagaManager.modules.company.entities.JobEntity;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    // contains - LIKE
    // select * from job where description like %filter%
    List<JobEntity>findByDescriptionContainingIgnoreCase(String filter);
}
