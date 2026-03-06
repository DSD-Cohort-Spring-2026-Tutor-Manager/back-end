package org.tutortoise.service.session;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;
import org.tutortoise.service.student.Student;
import org.tutortoise.service.student.StudentDTO;
import org.tutortoise.service.student.StudentRepository;
import org.tutortoise.service.subject.Subject;
import org.tutortoise.service.subject.SubjectDTO;
import org.tutortoise.service.tutor.Tutor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import static jdk.internal.classfile.Classfile.build;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    public SessionService(SessionRepository sessionRepository, ParentRepository parentRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;

    }

    public List<SessionDTO> getSessions(String tutorId, String status) {
        List<Session> sessions;
        if (StringUtils.isBlank(tutorId) && StringUtils.isBlank(status)) {
            sessions = sessionRepository.findAll();
        } else if (StringUtils.isBlank(status)
                || Strings.CI.equals(status, SessionStatus.all.toString())) {
            sessions = sessionRepository.findByTutorTutorId(Integer.parseInt(tutorId));
        } else {
            sessions =
                    sessionRepository.findByTutorTutorIdAndSessionStatus(
                            Integer.parseInt(tutorId), SessionStatus.session(status.toLowerCase()));
        }

        return sessions.stream().map(SessionDTO::convertToDTO).toList();
    }

    public List<SessionStudentData> findStudentInfoByParent(Integer parentId) {
        List<SessionStudentData> sessions = sessionRepository.findStudentInfoByParent(parentId);
        if (CollectionUtils.isEmpty(sessions)) {
            return Collections.emptyList();
        }
        return sessions;
    }

    public int findSessionCountByParentIdAndStatus(Integer parentId, String status) {
        if (status == null) {
            return sessionRepository.countSessionByParentParentId(parentId);
        }

        return sessionRepository.findSessionCountByParentIdAndStatus(parentId, status);
    }

    public List<StudentDTO> getStudentProgressBySubject(Integer parentId) {
        List<StudentDTO> studentDTOList = new ArrayList<>();

        List<SessionStudentSubjectData> studentProgressBySubject =
                sessionRepository.findStudentProgressBySubject(parentId);

        if (CollectionUtils.isEmpty(studentProgressBySubject)) {
            return studentDTOList;
        }

        studentProgressBySubject.forEach(
                element -> {
                    boolean isExist =
                            studentDTOList.stream()
                                    .noneMatch(student -> student.getStudentId().equals(element.studentId()));
                    if (isExist) {
                        StudentDTO studentDTO =
                                StudentDTO.builder()
                                        .studentId(element.studentId())
                                        .studentName(element.studentName())
                                        .subjects(new ArrayList<>())
                                        .build();

                        SubjectDTO subjectDTO =
                                SubjectDTO.builder()
                                        .subjectId(element.subjectId())
                                        .subjectName(element.subject())
                                        .totalSubjectHoursCompleted(element.totalSessionsHoursCompleted())
                                        .totalSubjectHours(element.totalSessionsHours())
                                        .progressPercentage(
                                                calculateProgressPercentage(
                                                        element.totalSessionsHoursCompleted(), element.totalSessionsHours()))
                                        .build();

                        studentDTO.getSubjects().add(subjectDTO);

                        studentDTOList.add(studentDTO);
                    } else {
                        studentDTOList.stream()
                                .filter(student -> student.getStudentId().equals(element.studentId()))
                                .findFirst()
                                .ifPresent(
                                        student -> {
                                            SubjectDTO subjectDTO =
                                                    SubjectDTO.builder()
                                                            .subjectId(element.subjectId())
                                                            .subjectName(element.subject())
                                                            .totalSubjectHoursCompleted(element.totalSessionsHoursCompleted())
                                                            .totalSubjectHours(element.totalSessionsHours())
                                                            .progressPercentage(
                                                                    calculateProgressPercentage(
                                                                            element.totalSessionsHoursCompleted(),
                                                                            element.totalSessionsHours()))
                                                            .build();
                                            student.getSubjects().add(subjectDTO);
                                        });
                    }
                });

        return studentDTOList;
    }

    public List<SessionDTO> getOpenSessions() {
        List<Session> openSessions = sessionRepository.findOpenSessions();
        return openSessions.stream().map(SessionDTO::convertToDTO).toList();
    }

    public SessionDTO assignStudentToSession(Integer sessionId, Integer parentId, Integer studentId) {
        Session session = sessionRepository.findById(sessionId).orElse(null);
        Parent parent = parentRepository.findById(parentId).orElse(null);
        Student student = studentRepository.findById(studentId).orElse(null);

        session.setParent(parent);
        session.setStudent(student);
        sessionRepository.save(session);
        return SessionDTO.convertToDTO(session);
    }

    private Double calculateProgressPercentage(Integer hoursCompleted, Integer totalHours) {
        if (hoursCompleted == null || totalHours == null || totalHours == 0) {
            return 0.0;
        }
        double percent = Math.round((hoursCompleted.doubleValue() / totalHours.doubleValue()) * 100);
        return Math.min(percent, 100.0);
    }

    public Session completeAndGradeSession(final Integer sessionId, final Integer tutorId, final int grade) {
        // Validate input parameters
        if (sessionId == null) {
            throw new IllegalArgumentException("Session id is required and cannot be null");
        }
        if (tutorId == null) {
            throw new IllegalArgumentException("Tutor id is required and cannot be null");
        }

        Optional<Session> sessionOptional = sessionRepository.findBySessionIdOrderByDatetimeStartedDesc(sessionId);
        if (sessionOptional.isEmpty()) {
            throw new IllegalArgumentException("Session not found with id: %d".formatted(sessionId));
        }

        Session session = sessionOptional.get();

        validateSessionBeforeCompleting(sessionId, tutorId, grade, session);

        session.setSessionStatus(SessionStatus.completed);
        session.setAssessmentPointsEarned(Double.valueOf(grade+""));
        return sessionRepository.save(session);
    }

    private void validateSessionBeforeCompleting(Integer sessionId, Integer tutorId, int grade, Session session) {
        if (session.getTutor() == null || !session.getTutor().getTutorId().equals(tutorId)) {
            throw new IllegalArgumentException("Tutor with id: %d is not assigned to session with id: %d".formatted(tutorId, sessionId));
        }

        if (session.getSessionStatus() == SessionStatus.completed) {
            throw new IllegalArgumentException("Session with id: %d is already in completed status.".formatted(sessionId));
        } else if(session.getSessionStatus() == SessionStatus.cancelled) {
            throw new IllegalArgumentException("Session with id: %d is cancelled.".formatted(sessionId));
        }

        if (session.getAssessmentPointsMax() != null
                && Double.valueOf(grade) > session.getAssessmentPointsMax()) {
            throw new IllegalArgumentException(
                    "Grade cannot be greater than maximum allowed grade of : %s"
                            .formatted(session.getAssessmentPointsMax()));
        }

    }
}
