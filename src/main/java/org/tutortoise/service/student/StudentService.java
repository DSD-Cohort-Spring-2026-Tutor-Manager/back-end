package org.tutortoise.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;
import org.tutortoise.service.session.SessionDTO;

@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final ParentRepository parentRepository;

  @Autowired
  public StudentService(StudentRepository studentRepository, ParentRepository parentRepository) {
    this.studentRepository = studentRepository;
    this.parentRepository = parentRepository;
  }

  public StudentDTO addStudent(Integer parentId, String firstName, String lastName) {

    Parent parent =
        parentRepository
            .findById(parentId)
            .orElseThrow(() -> new RuntimeException("Parent not found"));

    Student student = new Student(parent, firstName, lastName);
    Student saved = studentRepository.save(student);
    return convertToDTO(saved);
  }

  public StudentDTO convertToDTO(Student student) {
    return StudentDTO.builder()
        .studentId(student.getStudentId())
        .parentId(student.getParent().getParentId())
        .studentName(student.getFirstName() + " " + student.getLastName())
        .notes(student.getNotes())
        .sessions(student.getSessions().stream().map(SessionDTO::convertToDTO).toList())
        .build();
  }
}
