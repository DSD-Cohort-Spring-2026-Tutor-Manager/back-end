package org.tutortoise.service.session;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    Integer countBySessionStatusInAndDatetimeStartedBetween(
            List<SessionStatus> statuses,
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
                    "SELECT * FROM session WHERE student_id_fk is NULL AND session_status = 'open' AND datetime_started > CURRENT_TIMESTAMP ORDER BY datetime_started ASC",
            nativeQuery = true)
    List<Session> findOpenSessions();


    @Transactional
    @Modifying
    @Query(
            value =
                    "UPDATE session SET session_status = 'cancelled' " +
                            "WHERE session_status = 'open' " +
                            "AND student_id_fk IS NULL " +
                            "AND datetime_started < CURRENT_TIMESTAMP",
            nativeQuery = true)
    void setPastSessionsAsCancelled();

    Optional<Session> findBySessionIdOrderByDatetimeStartedDesc(Integer sessionId);


    List<Session> findByDatetimeStartedBetween(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime);

    List<Session> findByParentParentId(Integer parentId);
}
