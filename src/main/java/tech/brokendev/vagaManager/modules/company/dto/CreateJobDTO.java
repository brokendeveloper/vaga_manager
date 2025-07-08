package tech.brokendev.vagaManager.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "job for junior software engineer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "gympass, health plans", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "internship, junior,  midlevel and senior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
