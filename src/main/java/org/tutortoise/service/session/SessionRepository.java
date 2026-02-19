package org.tutortoise.service.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByTutorTutorId(Integer tutorId);
    List<Session> findByTutorTutorIdAndSessionStatus(Integer tutorId,SessionStatus  status);
}
