package org.tutortoise.service.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tutortoise.service.parent.ParentDTO;
import org.tutortoise.service.parent.ParentService;
import org.tutortoise.service.parent.RegisterParentDTO;
import org.tutortoise.service.tutor.RegisterTutorDTO;
import org.tutortoise.service.tutor.TutorService;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminController {

    private final AdminDashboardService adminDashboardService;
    private final ParentService parentService ;
    private final TutorService tutorService;


    public AdminController(AdminDashboardService adminDashboardService, ParentService parentService, TutorService tutorService) {
        this.adminDashboardService = adminDashboardService;
        this.parentService = parentService;
        this.tutorService = tutorService;
    }

    @GetMapping
    public ResponseEntity<AdminDashboardDTO> getDashboard(
            @PageableDefault(size = 10, sort = "datetimeStarted", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(adminDashboardService.getDashboard(pageable));
    }

    @Operation(summary = "Create new parent")
    @PostMapping("/createParent")
    public ResponseEntity<RegisterParentDTO> createParent(
            @RequestBody RegisterParentDTO request) {

        RegisterParentDTO parent = parentService.createParent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(parent);
    }



    @Operation(summary = "Create new tutor")
    @PostMapping("/createTutor")
    public ResponseEntity<RegisterTutorDTO> createTutor(
            @RequestBody RegisterTutorDTO request) {

        RegisterTutorDTO tutor = tutorService.createTutor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tutor);
    }
}
