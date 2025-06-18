package tech.brokendev.vagaManager.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.exceptions.UserFoundException;
import tech.brokendev.vagaManager.modules.company.entities.CompanyEntity;
import tech.brokendev.vagaManager.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent(company -> {
                    throw new UserFoundException(company.getUsername() + " already exists");
                });
        return this.companyRepository.save(companyEntity);
    }
}
