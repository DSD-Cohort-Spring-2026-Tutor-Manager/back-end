package org.tutortoise.service.parent;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tutortoise.service.advice.HttpRestResponse;
import org.tutortoise.service.session.SessionService;
import org.tutortoise.service.session.SessionStatus;
import org.tutortoise.service.session.SessionStudentData;
import org.tutortoise.service.student.StudentDTO;
import org.tutortoise.service.student.StudentService;

@Service
@RequiredArgsConstructor
public class ParentService {

  private final SessionService sessionService;
  private final ParentRepository parentRepository;

  public ParentDTO getParentInfo(
      @Positive(message = "Parent id must be positive integer") Integer parentId) {

    ParentDTO parentDTO = new ParentDTO();
    parentDTO.setStatus(HttpStatus.OK);
    parentDTO.setOperationStatus(HttpRestResponse.SUCCESS);

    Optional<Parent> optionalParent = parentRepository.findById(parentId);

    if(optionalParent.isEmpty()) {
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
}
