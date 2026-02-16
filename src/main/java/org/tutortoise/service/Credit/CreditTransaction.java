package org.tutortoise.service.Credit;

import jakarta.persistence.*;
import org.tutortoise.service.parent.Parent;

import java.time.LocalDateTime;

@Entity
@Table(name="credit_transaction")
public class CreditTransaction {

    @Id
    @Column(name="transaction_id")
    private int transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id_fk", nullable = false)
    private Parent parent;

    @Column(name="tutor_id_fk")
    private int tutorId;

    @Column(name="session_id_fk")
    private int sessionId;

    @Column(name="dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name="number_of_credits")
    private Integer numberOfCredits;

    @Column(name="transaction_total")
    private Double transactionTotal;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private TransactionType type;

    public CreditTransaction() {
    }

    public CreditTransaction(int transactionId, Parent parent, int tutorId, int sessionId, LocalDateTime dateTime, Integer numberOfCredits, Double transactionTotal, TransactionType type) {
        this.transactionId = transactionId;
        this.parent = parent;
        this.tutorId = tutorId;
        this.sessionId = sessionId;
        this.dateTime = dateTime;
        this.numberOfCredits = numberOfCredits;
        this.transactionTotal = transactionTotal;
        this.type = type;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreditTransaction{" +
                "transactionId=" + transactionId +
                ", parent=" + parent +
                ", tutorId=" + tutorId +
                ", sessionId=" + sessionId +
                ", dateTime=" + dateTime +
                ", numberOfCredits=" + numberOfCredits +
                ", transactionTotal=" + transactionTotal +
                ", type=" + type +
                '}';
    }
}
