package org.tutortoise.service.session;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tutortoise.service.student.StudentDTO;
import org.tutortoise.service.subject.SubjectDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
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
                                        .studentName(element.status())
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

    private Double calculateProgressPercentage(Integer hoursCompleted, Integer totalHours) {
        if (hoursCompleted == null || totalHours == null || totalHours == 0) {
            return 0.0;
        }
        double percent = Math.round((hoursCompleted.doubleValue() / totalHours.doubleValue()) * 100);
        return Math.min(percent, 100.0);
    }
}
