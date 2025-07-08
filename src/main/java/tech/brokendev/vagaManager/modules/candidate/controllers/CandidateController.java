package tech.brokendev.vagaManager.modules.candidate.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.brokendev.vagaManager.modules.candidate.CandidateEntity;
import tech.brokendev.vagaManager.modules.candidate.dto.ProfileCandidateResponseDTO;
import tech.brokendev.vagaManager.modules.candidate.useCases.CreateCandidateUseCase;
import tech.brokendev.vagaManager.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import tech.brokendev.vagaManager.modules.candidate.useCases.ProfileCandidateUseCase;
import tech.brokendev.vagaManager.modules.company.entities.JobEntity;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
        try{
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.ok().body(result);

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidate", description = "Information of candidate")
    @Operation(summary = "profile of candidate",
        description = "this function is reponsible for searching the informations of candidate"

    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class)),
                    })
            }
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object>get(HttpServletRequest request){
        var idCandidate = request.getAttribute("candidate_id");
        try{
            var result = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(result);
            
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidate", description = "Informations about the candidate")
    @Operation(summary = "Listing all jobs for candidate", description = "This function is responsable for list all jobs with filter to candidate")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))),
                    }),
                    @ApiResponse(responseCode = "400", description = "user not found")
            }
    )
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity>findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
}
