package org.tutortoise.service.Credit;

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
    public void buyCredits(int parentId, int credits, double amount) {

        Parent parent = parentRepository.
                findById(parentId).orElseThrow(() -> new RuntimeException("Parent not found"));
        parent.setCurrentCreditAmount(parent.getCurrentCreditAmount()+ credits);


    CreditTransaction transaction = new CreditTransaction();
    transaction.setNumberOfCredits(credits);
    transaction.setTransactionTotal(amount);
    transaction.setType(TransactionType.PURCHASE);
    transaction.setDateTime(LocalDateTime.now());
    parent.addTransaction(transaction);
    parentRepository.save(parent);
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
