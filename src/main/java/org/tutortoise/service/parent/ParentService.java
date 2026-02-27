package org.tutortoise.service.parent;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tutortoise.service.advice.HttpRestResponse;
import org.tutortoise.service.session.SessionService;
import org.tutortoise.service.session.SessionStatus;
import org.tutortoise.service.session.SessionStudentData;
import org.tutortoise.service.student.StudentDTO;
import org.tutortoise.service.subject.SubjectDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final SessionService sessionService;
    private final ParentRepository parentRepository;

    public ParentDTO getStudentDetailsByParent(
            @Positive(message = "Parent id must be positive integer") Integer parentId) {

        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setStatus(HttpStatus.OK);
        parentDTO.setOperationStatus(HttpRestResponse.SUCCESS);

        Optional<Parent> optionalParent = parentRepository.findById(parentId);

        if (optionalParent.isEmpty()) {
            parentDTO.setStatus(HttpStatus.NOT_FOUND);
            parentDTO.setMessage(String.format("Parent with id : %s not found", parentId));
            parentDTO.setOperationStatus(HttpRestResponse.FAILED);
            return parentDTO;
        }

        parentDTO.setCreditBalance(optionalParent.map(Parent::getCurrentCreditAmount).orElse(0.0));
        parentDTO.setParentName(optionalParent.map(parent -> parent.getFirstName() + " " + parent.getLastName()).orElse("N/A"));
        parentDTO.setParentEmail(optionalParent.map(Parent::getEmail).orElse("N/A"));

        List<SessionStudentData> sessionStudentDataList =
                sessionService.findStudentInfoByParent(parentId);
        if (CollectionUtils.isEmpty(sessionStudentDataList)) {
            parentDTO.setStatus(HttpStatus.NOT_FOUND);
            parentDTO.setMessage(
                    String.format("Session information for Parent : %s not found", parentId));
            parentDTO.setOperationStatus(HttpRestResponse.FAILED);
            return parentDTO;
        }

        Map<Integer, StudentDTO> studentDTOMap = new HashMap<>();
        sessionStudentDataList.forEach(
                sessionDTO -> {

                    studentDTOMap.compute(sessionDTO.studentId(), (studentId, studentDTO) -> {

                        if (studentDTO == null) {
                            studentDTO =
                                    StudentDTO.builder()
                                            .studentId(studentId)
                                            .parentId(parentId)
                                            .studentName(sessionDTO.firstName() + " " + sessionDTO.lastName())
                                            .previousScore(
                                                    calculateScore(
                                                            sessionDTO.assessmentPointsEarned(),
                                                            sessionDTO.assessmentPointsMax()))
                                            .build();
                            studentDTO.setLatestScore(studentDTO.getPreviousScore());
                        } else {
                            studentDTO.setPreviousScore(
                                    calculateScore(
                                            sessionDTO.assessmentPointsEarned(), sessionDTO.assessmentPointsMax()));
                        }
                        return studentDTO;
                    });
                });

        parentDTO.setStudents(new ArrayList<>(studentDTOMap.values()));
        parentDTO.setSessionCount(sessionService.findSessionCountByParentIdAndStatus(parentId, SessionStatus.completed.name()));
        parentDTO.setParentId(parentId);
        return parentDTO;
    }

    private Integer calculateScore(
            BigDecimal assessmentPointsEarned, BigDecimal assessmentPointsMax) {
        if (assessmentPointsEarned == null
                || assessmentPointsMax == null
                || assessmentPointsEarned.compareTo(BigDecimal.ZERO) == 0
                || assessmentPointsMax.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        return (int)
                Math.round(
                        (assessmentPointsEarned.doubleValue() / assessmentPointsMax.doubleValue()) * 100);
    }

    public ParentDTO getStudentProgressBySubject(final Integer parentId, final Integer studentId, final Integer subjectId) {
        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setStatus(HttpStatus.OK);
        parentDTO.setOperationStatus(HttpRestResponse.SUCCESS);

        parentRepository.findById(parentId).ifPresentOrElse(
                parent -> {
                    parentDTO.setParentId(parent.getParentId());
                    parentDTO.setParentName(parent.getFirstName() + " " + parent.getLastName());
                    parentDTO.setParentEmail(parent.getEmail());
                    parentDTO.setCreditBalance(parent.getCurrentCreditAmount());
                    List<StudentDTO> studentDTOList = sessionService.getStudentProgressBySubject(parentId);

                    if (studentId != null) {
                        studentDTOList = studentDTOList.stream()
                                .filter(student -> student.getStudentId().equals(studentId))
                                .collect(Collectors.toList());
                    }

                    if (subjectId != null) {
                        AtomicReference<List<SubjectDTO>> list = new AtomicReference<>(new ArrayList<>());
                        studentDTOList
                                .forEach(student -> {

                                    list.set(student.getSubjects().stream()
                                            .filter(subject -> subject.getSubjectId().equals(subjectId))
                                            .toList());

                                    student.setSubjects(list.get());
                                });
                    }

                    parentDTO.setStudents(studentDTOList);
                },
                () -> {
                    parentDTO.setStatus(HttpStatus.NOT_FOUND);
                    parentDTO.setMessage(String.format("Parent with id : %s not found", parentId));
                    parentDTO.setOperationStatus(HttpRestResponse.FAILED);
                }
        );

        return parentDTO;
    }

    public ParentDTO getStudentInformation(final Integer parentId, final Integer studentId) {
        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setParentId(parentId);
        parentDTO.setStatus(HttpStatus.OK);
        parentDTO.setOperationStatus(HttpRestResponse.SUCCESS);

        parentRepository.findById(parentId).ifPresentOrElse(parent -> {
            parentDTO.setParentName(parent.getFirstName() + " " + parent.getLastName());
            parentDTO.setParentEmail(parent.getEmail());
            parentDTO.setCreditBalance(parent.getCurrentCreditAmount());
            parentDTO.setSessionCount(sessionService.findSessionCountByParentIdAndStatus(parentId, null));

            if (CollectionUtils.isEmpty(parent.getStudents())) {
                parentDTO.setStatus(HttpStatus.NOT_FOUND);
                parentDTO.setMessage(String.format("No students found for Parent with id : %s", parentId));
                parentDTO.setOperationStatus(HttpRestResponse.FAILED);
                return;
            }

            List<StudentDTO> studentDTOList = parent.getStudents().stream()
                    .filter(student -> studentId == null || student.getStudentId().equals(studentId))
                    .map(student -> StudentDTO.builder()
                    .studentName(student.getFirstName() + " " + student.getLastName())
                    .studentId(student.getStudentId())
                    .build()).toList();

            parentDTO.setStudents(studentDTOList);
        }, () -> {
            parentDTO.setStatus(HttpStatus.NOT_FOUND);
            parentDTO.setMessage(String.format("Parent with id : %s not found", parentId));
            parentDTO.setOperationStatus(HttpRestResponse.FAILED);
        });

        return parentDTO;
    }
}
