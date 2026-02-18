package org.tutortoise.service.credit;

public class CreditRequest {
    private Integer parentId;
    private Integer credits;
    private double amount;

    public CreditRequest() {
    }

    public CreditRequest(Integer parentId, Integer credits, double amount) {
        this.parentId = parentId;
        this.credits = credits;
        this.amount = amount;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
