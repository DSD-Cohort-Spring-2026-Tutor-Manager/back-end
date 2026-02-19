package org.tutortoise.service.session;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionDTO> getAllSessions(Integer tutorId){
        List <Session> sessions = sessionRepository.findByTutorTutorId(tutorId);
        return sessions.stream().map(this::convertToDTO).toList();
    }

    private SessionDTO convertToDTO(Session session) {
        return new SessionDTO(session.getSessionId(),
                session.getParent().getParentId(),
                session.getStudent().getStudentId(),
                session.getTutor().getTutorId(),
                session.getSessionStatus().name(),
                session.getDurationsHours(),
                session.getDatetimeStarted(),
                session.getAssessmentPointsEarned(),
                session.getAssessmentPointsGoal(),
                session.getAssessmentPointsMax());
    }

    public List<SessionDTO> getSessionsByStatus(
            Integer tutorId,
            SessionStatus status
    ){
        List<Session> sessions = sessionRepository.
                findByTutorTutorIdAndSessionStatus(tutorId,status);
        return sessions.stream().map(this::convertToDTO).toList();
    }
}
