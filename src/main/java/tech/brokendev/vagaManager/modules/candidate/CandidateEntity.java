package tech.brokendev.vagaManager.modules.candidate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "candidateExampleName", requiredMode = Schema.RequiredMode.REQUIRED, description = "Name of candidate")
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\S+$", message = "O campo [ username ] não deve conter espaços")
    private String username;

    @Email(message = "o campo deve conter um e-mail válido")
    @Schema(example = "candidateEmail@example.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email of candidate")
    private String email;

    @Length(min = 10, max = 100)
    @Schema(example = "admin@124$", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "password of candidate")
    private String password;

    @Schema(example = "candidate Example description -> Java Dev")
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
