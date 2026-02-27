package org.tutortoise.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tutortoise.service.credit.CreditTransactionRepository;
import org.tutortoise.service.credit.TransactionType;
import org.tutortoise.service.session.Session;
import org.tutortoise.service.session.SessionDTO;
import org.tutortoise.service.session.SessionRepository;
import org.tutortoise.service.session.SessionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminDashboardService {

    private final CreditTransactionRepository creditTransactionRepository;
    private final SessionRepository sessionRepository;


    public AdminDashboardService(CreditTransactionRepository creditTransactionRepository, SessionRepository sessionRepository) {
        this.creditTransactionRepository = creditTransactionRepository;
        this.sessionRepository = sessionRepository;
    }

    public AdminDashboardDTO getDashboard(Pageable pageable){
        LocalDateTime start = DateUtility.getStartOfWeek();
        LocalDateTime end = DateUtility.getEndOfWeek();

        Integer totalCredits = creditTransactionRepository.sumByTypeAndDateTimeBetween
                (TransactionType.purchase,start,end);

        if(totalCredits==null) totalCredits = 0;

        Integer totalSessions = sessionRepository. countBySessionStatusAndDatetimeStartedBetween(SessionStatus.scheduled,
                start,end);

        Page<Session> sessions = sessionRepository.findBySessionStatusAndDatetimeStartedBetween
                (SessionStatus.scheduled, start,end,pageable);

        List<SessionDTO> sessionDTOS = sessions.map(this::convertToDTO).getContent();

        return new AdminDashboardDTO(
                totalCredits,
                totalSessions,
                sessionDTOS
        );


    }

    public  SessionDTO convertToDTO(Session session) {

        return new SessionDTO(
                session.getSessionId(),
                session.getParent().getParentId(),
                session.getStudent().getStudentId(),
                session.getStudent().getFirstName(),
                session.getStudent().getLastName(),
                session.getStudent().getNotes(),
                session.getSubject().getSubject(),
                session.getTutor().getTutorId(),
                session.getTutor().getFirstName() + " " + session.getTutor().getLastName(),
                session.getDurationsHours(),
                session.getSessionStatus().name(),
                session.getDatetimeStarted(),
                session.getAssessmentPointsEarned(),
                session.getAssessmentPointsGoal(),
                session.getAssessmentPointsMax());
    }
}
