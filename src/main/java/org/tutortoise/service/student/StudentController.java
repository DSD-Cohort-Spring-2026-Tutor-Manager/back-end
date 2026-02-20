package org.tutortoise.service.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
