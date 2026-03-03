package org.tutortoise.service.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

  private final  StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping("/add")
  public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentRequest request) {
    StudentDTO student = studentService.addStudent(
            request.getParentId(),
            request.getFirstName(),
            request.getLastName()
    );
    return ResponseEntity.ok(student);
  }

  @Operation(
          summary = "Get student note",
          description = "Returns the note of a student"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",
                  description = "Student note retrieved successfully"),
          @ApiResponse(responseCode = "404",
                  description = "Student or tutor  not found")
  })
  @GetMapping("/{studentId}/note")
  public ResponseEntity<StudentNoteDTO> getStudentNote(
          @RequestParam Integer tutorId,
          @PathVariable Integer studentId) {

    return ResponseEntity.ok(
            studentService.getStudentNote(tutorId, studentId)
    );
  }

  @Operation(
          summary = "Update student note",
          description = "Updates the note of a student for the tutor"
  )

  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",
                  description = "Note updated successfully"),
          @ApiResponse(responseCode = "404",
                  description = "Student or tutor not found"),
  })

  @PutMapping("/{studentId}/note")
  public ResponseEntity<StudentNoteDTO> updateStudentNote(
          @RequestParam Integer tutorId,
          @PathVariable Integer studentId,
          @RequestBody StudentNoteDTO request) {

    StudentNoteDTO updated =
            studentService.updateStudentNote(
                    tutorId,
                    studentId,
                    request.getNotes()
            );

    return ResponseEntity.ok(updated);
  }
}
