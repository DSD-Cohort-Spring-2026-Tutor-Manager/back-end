package org.tutortoise.service.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tutortoise.service.parent.Parent;

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
}
