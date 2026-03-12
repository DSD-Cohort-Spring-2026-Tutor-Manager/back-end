package org.tutortoise.service.admin;

import lombok.Data;
import org.tutortoise.service.session.SessionDTO;

import java.util.List;

@Data
public class AdminDashboardDTO {

    private Integer weeklyCreditSold;
    private Integer lastWeeklyCreditSold;
    private Integer weeklySessionsBooked;
    private Integer lastWeeklySessionsBooked;
    private Double weeklyCashRevenue;
    private Double lastWeeklyCashRevenue;


    public AdminDashboardDTO() {
    }

    public AdminDashboardDTO(Integer weeklyCreditSold, Integer lastWeeklyCreditSold, Integer weeklySessionsBooked, Integer lastWeeklySessionsBooked, Double weeklyCashRevenue, Double lastWeeklyCashRevenue) {
        this.weeklyCreditSold = weeklyCreditSold;
        this.lastWeeklyCreditSold = lastWeeklyCreditSold;
        this.weeklySessionsBooked = weeklySessionsBooked;
        this.lastWeeklySessionsBooked = lastWeeklySessionsBooked;
        this.weeklyCashRevenue = weeklyCashRevenue;
        this.lastWeeklyCashRevenue = lastWeeklyCashRevenue;
    }
}
