package org.tutortoise.service.session;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Operation(summary = "Retrieves all sessions without filters", description = "This API should return a list of all sessions without applying any filters. " +
            "It will return all sessions regardless of the tutor, parent or session status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful session retrieval"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getSessions(null, null));
    }

    @Operation(summary = "Retrieves sessions by filters", description = "This API should return a list of sessions " +
            "based on the provided filters. The tutorId filter allows you to retrieve sessions for a specific tutor, " +
            "while the status filter allows you to retrieve sessions based on their status (e.g., scheduled, completed, " +
            "cancelled, all). If no filters are provided, it will return all sessions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful session retrieval"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "/tutor/{tutorId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<SessionDTO>> getSessions(
            @PathVariable @NotBlank @Positive(message = "tutorId needs to be a positive integer only") String tutorId,
            @RequestParam String status) {
        if( StringUtils.isBlank(tutorId) ){
            throw new IllegalArgumentException("Tutor id cannot be null or blank");
        }
        return ResponseEntity.ok(sessionService.getSessions(tutorId, status));
    }

}
