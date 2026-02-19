package org.tutortoise.service.session;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getTutorSessions(Integer tutorId){
        return sessionRepository.findByTutorTutorId(tutorId);
    }
}
