package org.tutortoise.service.admin;

import lombok.Data;
import org.tutortoise.service.session.SessionDTO;

import java.util.List;

@Data
public class AdminDashboardDTO {

    private Integer weeklyCreditSold;
    private Integer weeklySessionsBooked;
    private List<SessionDTO>  bookedSessions;

    public AdminDashboardDTO() {
    }

    public AdminDashboardDTO(Integer weeklyCreditSold, Integer weeklySessionsBooked, List<SessionDTO> bookedSessions) {
        this.weeklyCreditSold = weeklyCreditSold;
        this.weeklySessionsBooked = weeklySessionsBooked;
        this.bookedSessions = bookedSessions;
    }

}
