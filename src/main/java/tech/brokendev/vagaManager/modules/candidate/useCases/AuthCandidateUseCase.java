package tech.brokendev.vagaManager.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.modules.candidate.CandidateRepository;
import tech.brokendev.vagaManager.modules.candidate.dto.AuthCandidateRequestDTO;
import tech.brokendev.vagaManager.modules.candidate.dto.AuthCandidateResponseDTO;

import javax.security.sasl.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.secret.token.candidate}")
    private String secretKeyCanditate;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO requestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(requestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("username / password incorrect");
                });

        var passwordMatches = this.passwordEncoder.matches(requestDTO.password(), candidate.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKeyCanditate);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("vagamanager")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .acessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
