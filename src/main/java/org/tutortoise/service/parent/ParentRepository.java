package org.tutortoise.service.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Optional<Parent> findByEmail(String email);
    Optional<Parent>findById(Integer parentId);


    @Query(value = """
    SELECT 
        p.parent_id AS parentId,
        p.first_name AS firstName,
        p.last_name AS lastName,
        p.email AS email,
        p.phone as phone,
        COALESCE(p.current_credit_amount, 0) AS currentCreditBalance,
        COUNT(s.student_id) AS numberOfStudents
    FROM parent p
    LEFT JOIN student s ON s.parent_id_fk = p.parent_id
    GROUP BY p.parent_id, p.first_name, p.last_name, p.email,p.phone, p.current_credit_amount
    ORDER BY COALESCE(p.current_credit_amount, 0) DESC
""", nativeQuery = true)
    List<Object[]> findParentsWithStudentCountNative();
}
