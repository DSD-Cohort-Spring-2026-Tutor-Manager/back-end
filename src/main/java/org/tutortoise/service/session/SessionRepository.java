package org.tutortoise.service.session;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
  List<Session> findByTutorTutorId(Integer tutorId);

  List<Session> findByTutorTutorIdAndSessionStatus(Integer tutorId, SessionStatus status);

  @Query(name = "findStudentInfoByParent")
  List<SessionStudentData> findStudentInfoByParent(Integer parentId);

  @Query(
      value =
          "SELECT count(*) FROM session WHERE parent_id_fk = :parentId AND session_status = :status",
      nativeQuery = true)
  int findSessionCountByParentIdAndStatus(Integer parentId, String status);

Integer countBySessionStatusAndDatetimeStartedBetween(
        SessionStatus status,
        LocalDateTime start,
        LocalDateTime end
);

  Page<Session> findBySessionStatusAndDatetimeStartedBetween(
          SessionStatus status,
          LocalDateTime start,
          LocalDateTime end,
          Pageable pageable
  );

}
