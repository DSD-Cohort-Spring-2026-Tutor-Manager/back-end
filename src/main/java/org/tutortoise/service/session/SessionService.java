package org.tutortoise.service.session;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;

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
}
