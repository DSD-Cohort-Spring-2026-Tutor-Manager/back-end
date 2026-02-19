package org.tutortoise.service.credit;

import java.time.LocalDateTime;

public class CreditTransactionDTO {

    private Integer transactionId;
    private Integer parentId;
    private Integer tutorId;
    private Integer sessionId;
    private LocalDateTime dateTime;
    private Integer numberOfCredits;
    private Double transactionTotal;
    private String type;

    public CreditTransactionDTO() {
    }

    public CreditTransactionDTO(Integer transactionId, Integer parentId, Integer tutorId, Integer sessionId, LocalDateTime dateTime, Integer numberOfCredits, Double transactionTotal, String type) {
        this.transactionId = transactionId;
        this.parentId = parentId;
        this.tutorId = tutorId;
        this.sessionId = sessionId;
        this.dateTime = dateTime;
        this.numberOfCredits = numberOfCredits;
        this.transactionTotal = transactionTotal;
        this.type = type;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
