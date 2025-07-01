package tech.brokendev.vagaManager.modules.candidate.useCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.brokendev.vagaManager.modules.candidate.CandidateRepository;
import tech.brokendev.vagaManager.modules.candidate.dto.ProfileCandidateResponseDTO;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;


    public ProfileCandidateResponseDTO execute(UUID idCandidate) {

        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("user not found");
                });

        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .name(candidate.getName())
                .build();

        return candidateDTO;

    }
}
