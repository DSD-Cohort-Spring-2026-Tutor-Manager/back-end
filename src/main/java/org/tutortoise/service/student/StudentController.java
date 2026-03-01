package org.tutortoise.service.student;

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

  @GetMapping("/{studentId}/note")
  public ResponseEntity<StudentNoteDTO> getStudentNote(
          @RequestParam Integer tutorId,
          @PathVariable Integer studentId) {

    return ResponseEntity.ok(
            studentService.getStudentNote(tutorId, studentId)
    );
  }


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
