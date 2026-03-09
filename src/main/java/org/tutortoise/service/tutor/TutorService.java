package org.tutortoise.service.tutor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tutortoise.service.session.Session;
import org.tutortoise.service.session.SessionService;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final SessionService sessionService;
    private final TutorRepository tutorRepository;

    public TutorDTO completeAndGradeSession(TutorSessionRequest request) {
        Session session = sessionService.completeAndGradeSession(
                request.getSessionId(),
                request.getTutorId(),
                request.getGrade()
        );

        return TutorDTO.builder()
                .tutorId(session.getTutor().getTutorId())
                .sessionId(session.getSessionId())
                .studentId(session.getStudent().getStudentId())
                .subjectId(session.getSubject().getId())
                .assessmentPointEarned(session.getAssessmentPointsEarned().intValue())
                .dateTime(session.getDatetimeStarted())
                .duration(session.getDurationsHours().intValue())
                .tutorName("%s %s".formatted(session.getTutor().getFirstName(), session.getTutor().getLastName()))
                .subject(session.getSubject().getSubject())
                .studentName("%s %s".formatted(session.getStudent().getFirstName(), session.getStudent().getLastName()))
                .build();
    }

    public RegisterTutorDTO createTutor(RegisterTutorDTO request) {

        Tutor tutor = new Tutor();
        tutor.setFirstName(request.getFirstName());
        tutor.setLastName(request.getLastName());
        tutor.setEmail(request.getEmail());
        tutor.setPhone(request.getPhone());
        tutor.setPasswordEncrypted(request.getPassword());

        Tutor saved = tutorRepository.save(tutor);

        return new RegisterTutorDTO(
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getPhone()
        );
    }

}
