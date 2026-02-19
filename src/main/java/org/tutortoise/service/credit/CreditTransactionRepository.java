package org.tutortoise.service.credit;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Integer> {
    List<CreditTransaction> findByParentParentId(Integer parentId);
}
