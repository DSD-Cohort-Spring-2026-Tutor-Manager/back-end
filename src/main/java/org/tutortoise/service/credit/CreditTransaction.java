package org.tutortoise.service.credit;

import jakarta.persistence.*;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.session.Session;
import org.tutortoise.service.tutor.Tutor;

import java.time.LocalDateTime;

@Entity
@Table(name="credittransaction")
public class CreditTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private Integer transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id_fk", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = true)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id_fk", nullable = true)
    private Session session;

    @Column(name="datetime_transaction", nullable = false)
    private LocalDateTime dateTime;

    @Column(name="number_of_credits")
    private Integer numberOfCredits;

    @Column(name="transaction_total_usd")
    private Double transactionTotal;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type")
    private TransactionType type;

    public CreditTransaction() {
    }

    public CreditTransaction(Integer transactionId, Parent parent, Tutor tutor, Session session, LocalDateTime dateTime, Integer numberOfCredits, Double transactionTotal, TransactionType type) {
        this.transactionId = transactionId;
        this.parent = parent;
        this.tutor = tutor;
        this.session = session;
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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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


}
