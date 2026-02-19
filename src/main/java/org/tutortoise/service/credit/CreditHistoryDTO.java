package org.tutortoise.service.credit;

import org.tutortoise.service.tutor.Tutor;

import java.time.LocalDateTime;

public class CreditHistoryDTO {
    private Integer transactionId;
    private Integer parentId;
    private Integer tutorId;
    private Integer sessionId;
    private Integer numberOfCredits;
    private Double transactionTotal;
    private String transactionType;
    private LocalDateTime transactionTime;

    public CreditHistoryDTO() {
    }

    public CreditHistoryDTO(Integer transactionId, Integer parentId, Integer tutorId, Integer sessionId, Integer numberOfCredits, Double transactionTotal, String transactionType, LocalDateTime transactionTime) {
        this.transactionId = transactionId;
        this.parentId = parentId;
        this.tutorId = tutorId;
        this.sessionId = sessionId;
        this.numberOfCredits = numberOfCredits;
        this.transactionTotal = transactionTotal;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(Integer numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public Double getTransactionTotal() {
        return transactionTotal;
    }

    public void setTransactionTotal(Double transactionTotal) {
        this.transactionTotal = transactionTotal;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
