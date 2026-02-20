package org.tutortoise.service.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
  @Query("SELECT s FROM Student s WHERE s.parent.parentId = :parentId")
  List<Student> findByParentId(@Param("parentId")Integer parentId);
}
