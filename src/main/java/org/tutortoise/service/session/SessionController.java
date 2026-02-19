package org.tutortoise.service.session;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutor")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions/{tutorId}")
    public ResponseEntity<List<SessionDTO>> getAllSessions(@PathVariable Integer tutorId) {
        return ResponseEntity.ok(sessionService.getAllSessions(tutorId));
    }

    @GetMapping("/{tutorId}/sessions/status")
    public ResponseEntity<List<SessionDTO>> getSessionsByStatus(
            @PathVariable Integer tutorId,
            @RequestParam SessionStatus status){
        return ResponseEntity.ok(sessionService.getSessionsByStatus(tutorId,status));
    }

}
