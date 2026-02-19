package org.tutortoise.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;

@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final ParentRepository parentRepository;

  @Autowired
  public StudentService(StudentRepository studentRepository, ParentRepository parentRepository) {
    this.studentRepository = studentRepository;
    this.parentRepository = parentRepository;
  }

  public void addStudent(Integer parentId, String firstName, String lastName) {
    Parent parent = parentRepository.findById(parentId).orElseThrow(() ->
            new RuntimeException("Parent not found"));

    Student student = new Student(parent, firstName, lastName);
    studentRepository.save(student);
  }

  public StudentDTO convertToDTO(Student student) {
    return new StudentDTO(
            student.getStudentId(),
            student.getParent().getParentId(),
            student.getFirstName(),
            student.getLastName(),
            student.getNotes(),
            student.getSessions()
    );
  }
}
