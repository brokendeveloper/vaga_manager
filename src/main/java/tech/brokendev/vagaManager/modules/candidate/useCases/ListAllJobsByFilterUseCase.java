package tech.brokendev.vagaManager.modules.candidate.useCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.modules.company.entities.JobEntity;
import tech.brokendev.vagaManager.modules.company.repositories.JobRepository;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter){

        return this.jobRepository.findByDescriptionContaining(filter);

    }
}
