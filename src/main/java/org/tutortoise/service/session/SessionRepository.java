package org.tutortoise.service.session;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tutortoise.service.parent.Parent;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query(name = "studentProgressBySubject")
    List<SessionStudentSubjectData> findStudentProgressBySubject(Integer parentId);

    int countSessionByParentParentId(Integer parentParentId);


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

    @Query(
            value =
                    "SELECT * FROM session WHERE student_id_fk is NULL AND session_status = 'scheduled'",
            nativeQuery = true)
    List<Session> findOpenSessions();
}
