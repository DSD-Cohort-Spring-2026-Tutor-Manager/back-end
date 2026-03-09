package org.tutortoise.service.tutor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tutor")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @Operation(
            summary = "Set the session session status to completed and assign a grade",
            description = "This API allows a tutor to complete a session and assign a grade to it. " +
                    "The tutor must provide the tutor id, session id, and the grade for the session. " +
                    "The response includes the updated session information with the assigned grade.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful information retrieval"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PutMapping("/assign-grade")
    public ResponseEntity<TutorDTO> assignGradeAndCompleteSession(@Validated @RequestBody TutorSessionRequest request) {
        TutorDTO tutorDTO = tutorService.completeAndGradeSession(request);
        return ResponseEntity.ok(tutorDTO);
    }
    
}
