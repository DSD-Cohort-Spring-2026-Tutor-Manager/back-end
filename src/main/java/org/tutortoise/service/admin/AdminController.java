package org.tutortoise.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminController {

    private final AdminDashboardService adminDashboardService;


    public AdminController(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping
    public ResponseEntity<AdminDashboardDTO> getDashboard(
            @PageableDefault(size = 10, sort = "datetimeStarted", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(adminDashboardService.getDashboard(pageable));
    }

}
