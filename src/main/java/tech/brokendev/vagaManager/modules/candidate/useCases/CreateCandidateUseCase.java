package tech.brokendev.vagaManager.modules.candidate.useCases;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.exceptions.UserFoundException;
import tech.brokendev.vagaManager.modules.candidate.CandidateEntity;
import tech.brokendev.vagaManager.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository repository;


    public CandidateEntity execute(CandidateEntity candidate) {
        this.repository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException("User " + user.getUsername() + " already exists");
                });
        return this.repository.save(candidate);
    }
}
