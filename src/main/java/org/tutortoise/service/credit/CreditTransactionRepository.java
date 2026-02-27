package org.tutortoise.service.credit;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.table.TableRowSorter;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Integer> {
    List<CreditTransaction> findByParentParentId(Integer parentId);

    @Query("""
        SELECT COALESCE(SUM(ct.numberOfCredits), 0)
        FROM CreditTransaction ct
        WHERE ct.type = :type
        AND ct.dateTime BETWEEN :start AND :end
    """)
    Integer sumByTypeAndDateTimeBetween(
            @Param("type") TransactionType type,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
