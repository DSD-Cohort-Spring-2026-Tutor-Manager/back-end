package org.tutortoise.service.tutor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {

    @PostMapping("/update-session")
    public @ResponseBody ResponseEntity<TutorDTO> createTutor(@RequestBody Tutor tutor) {
        TutorDTO tutorDTO = new TutorDTO();
        return ResponseEntity.ok(tutorDTO);
    }

}
