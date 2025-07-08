package tech.brokendev.vagaManager.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "java developer")
    private String description;

    @Schema(example = "username")
    private String username;

    @Schema(example = "example@email.com")
    private String email;

    private UUID id;

    @Schema(example = "nameExample")
    private String name;

}
