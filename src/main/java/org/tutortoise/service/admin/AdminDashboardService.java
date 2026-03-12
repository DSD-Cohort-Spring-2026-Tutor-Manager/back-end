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
        LocalDateTime lastWeekStart = DateUtility.getStartOfLastWeek();
        LocalDateTime lastWeekEnd = DateUtility.getEndOfLastWeek();

        Integer totalCreditsThisWeeK = creditTransactionRepository.sumByTypeAndDateTimeBetween
                (TransactionType.purchase,start,end);

        Integer totalCreditLastWeek = creditTransactionRepository.sumByTypeAndDateTimeBetween
                (TransactionType.purchase, lastWeekStart,lastWeekEnd);

        if(totalCreditsThisWeeK==null) totalCreditsThisWeeK = 0;
        if(totalCreditLastWeek==null) totalCreditLastWeek = 0;

        List<SessionStatus> statuses = List.of(
                SessionStatus.scheduled,
                SessionStatus.completed
        );
        Integer totalSessionsThisWeek = sessionRepository. countBySessionStatusInAndDatetimeStartedBetween(statuses,
                start,end);

        Integer totalSessionsLastWeek = sessionRepository. countBySessionStatusInAndDatetimeStartedBetween(statuses,
                lastWeekStart,lastWeekEnd);

        Double totalRevenueThisWeek = creditTransactionRepository.getTransactionTotalInDateRange(start,
                end);

        Double totalRevenueLastWeek = creditTransactionRepository.getTransactionTotalInDateRange(lastWeekStart,
                lastWeekEnd);




        return new AdminDashboardDTO(
                totalCreditsThisWeeK,
                totalCreditLastWeek,
                totalSessionsThisWeek,
                totalSessionsLastWeek,
                totalRevenueThisWeek,
                totalRevenueLastWeek

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
