package org.tutortoise.service.Credit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Integer> {
    List<CreditTransaction> findByParentParentId(Integer parentId);
}
