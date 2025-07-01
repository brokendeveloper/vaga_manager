package tech.brokendev.vagaManager.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.modules.company.dto.AuthCompanyDTO;
import tech.brokendev.vagaManager.modules.company.dto.AuthCompanyResponseDTO;
import tech.brokendev.vagaManager.modules.company.repositories.CompanyRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.secret.token}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO){
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username / Password incorrect");
                });

                // match of passwords
        var passwordsMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!passwordsMatches) {
            throw new BadCredentialsException("Bad credentials");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("vagamanager")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .acessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;

    }
}
