package org.tutortoise.service.session;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;
import org.tutortoise.service.parent.ParentService;
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

    public SessionDTO createSession(Tutor tutor, Subject subject, Integer year, Integer month, Integer day, Integer hour,
                                    Integer minute) {
        Session session = Session.builder()
                .tutor(tutor)
                .subject(subject)
                .datetimeStarted(LocalDateTime.of(year, month, day, hour, minute))
                .sessionStatus(SessionStatus.scheduled)
                .durationsHours(1.0)
                .build();
        return SessionDTO.convertToDTO(session);
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
        if( status == null ) {
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
}
