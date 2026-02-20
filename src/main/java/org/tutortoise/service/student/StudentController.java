package org.tutortoise.service.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @PostMapping("/get/{parentId}")
  public ResponseEntity<List<StudentDTO>> getStudentsForParent(@PathVariable Integer parentId) {
    return ResponseEntity.ok(studentService.getStudentsForParent(parentId));
  }
}
