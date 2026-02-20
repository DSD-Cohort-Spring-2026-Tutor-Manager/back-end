package org.tutortoise.service.parent;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Optional<Parent> findByEmail(String email);
    Optional<Parent>findById(Integer parentId);
}
