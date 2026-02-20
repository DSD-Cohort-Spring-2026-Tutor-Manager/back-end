package org.tutortoise.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;
import org.tutortoise.service.session.Session;

import java.util.List;

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

    // Get Parent by parentId
    Parent parent = parentRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent not found"));

    Student student = new Student(parent, firstName, lastName);
    Student saved = studentRepository.save(student);
    return convertToDTO(saved);
  }

  public StudentDTO convertToDTO(Student student) {
    return new StudentDTO(
            student.getStudentId(),
            student.getParent().getParentId(),
            student.getFirstName(),
            student.getLastName(),
            student.getNotes(),
            student.getSessions().stream()
                    .map(Session::getSessionId)
                    .toList()
    );
  }

  public List<StudentDTO> getStudentsForParent(Integer parentId) {
    return studentRepository.findByParentId(parentId).stream()
            .map(this :: convertToDTO)
            .toList();
  }
}
