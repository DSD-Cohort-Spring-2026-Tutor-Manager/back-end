package org.tutortoise.service.credit;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditService {

    private final ParentRepository parentRepository;
    private  final CreditTransactionRepository transactionRepository;

    public CreditService(ParentRepository parentRepository, CreditTransactionRepository transactionRepository) {
        this.parentRepository = parentRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public CreditResponseDTO buyCredits(CreditRequest creditRequest) {

        Parent parent = parentRepository.
                findById(creditRequest.getParentId()).orElseThrow(() -> new RuntimeException("Parent not found"));
        double updatedCredit = parent.getCurrentCreditAmount()+ creditRequest.getCredits();
        parent.setCurrentCreditAmount(updatedCredit);


    CreditTransaction transaction = new CreditTransaction();
    transaction.setNumberOfCredits(creditRequest.getCredits());
    transaction.setTransactionTotal(creditRequest.getAmount());
    transaction.setParent(parent);
    transaction.setType(TransactionType.purchase);
    transaction.setDateTime(LocalDateTime.now());

        parentRepository.save(parent);
        parent.addTransaction(transaction);
        transactionRepository.save(transaction);
        return new CreditResponseDTO(
                parent.getParentId(),
                creditRequest.getCredits(),
                creditRequest.getAmount(),
               updatedCredit,LocalDateTime.now()

        );


}

public Double getBalance(int parentId){
        return parentRepository.findById(parentId)
                .orElseThrow(()-> new RuntimeException("Parent not found")).
                getCurrentCreditAmount();
}

public List<CreditTransaction> getHistory(Integer parentId) {
        return transactionRepository.findByParentParentId(parentId);
}
}
