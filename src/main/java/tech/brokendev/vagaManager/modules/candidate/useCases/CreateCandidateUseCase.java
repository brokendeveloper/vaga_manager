package tech.brokendev.vagaManager.modules.candidate.useCases;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.exceptions.UserFoundException;
import tech.brokendev.vagaManager.modules.candidate.CandidateEntity;
import tech.brokendev.vagaManager.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidate) {
        this.repository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException("User " + user.getUsername() + " already exists");
                });

        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);

        return this.repository.save(candidate);
    }
}
