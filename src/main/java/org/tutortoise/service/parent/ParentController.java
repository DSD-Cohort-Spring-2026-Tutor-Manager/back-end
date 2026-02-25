package org.tutortoise.service.parent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

  private final ParentService parentService;

  @Operation(
      summary = "Retrieves student sessions with progress",
      description =
          "This API retrieves the session information for a specific parent, including the "
              + "sessions of their students and the progress of each student in their subjects."
              + "The response includes the parent details, a list of their students, and for each "
              + "student, a list of their sessions and their progress in each subject.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful information retrieval"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(value = "/{parentId}", produces = "application/json")
  public @ResponseBody ResponseEntity<ParentDTO> getParentSessionInfo(
      @PathVariable @Positive(message = "Parent id must be positive integer") Integer parentId) {
    ParentDTO parentDTO = parentService.getParentInfo(parentId);
    return ResponseEntity.ok(parentDTO);
  }
}
