package org.tutortoise.service.session;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByTutorTutorId(Integer tutorId);
}
