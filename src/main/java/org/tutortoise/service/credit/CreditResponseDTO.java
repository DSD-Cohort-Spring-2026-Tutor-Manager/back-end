package org.tutortoise.service.credit;

import java.time.LocalDateTime;

public class CreditResponseDTO {

    private Integer parentId;
    private Integer creditsPurchased;
    private Double amountPaid;
    private Double updatedBalance;
    private LocalDateTime transactionTime;

    public CreditResponseDTO() {
    }

    public CreditResponseDTO(Integer parentId, Integer creditsPurchased, Double amountPaid, Double updatedBalance, LocalDateTime transactionTime) {
        this.parentId = parentId;
        this.creditsPurchased = creditsPurchased;
        this.amountPaid = amountPaid;
        this.updatedBalance = updatedBalance;
        this.transactionTime = transactionTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCreditsPurchased() {
        return creditsPurchased;
    }

    public void setCreditsPurchased(Integer creditsPurchased) {
        this.creditsPurchased = creditsPurchased;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(Double updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
