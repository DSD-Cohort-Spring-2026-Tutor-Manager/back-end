package org.tutorial.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tutorial.demo.entity.Parent;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
}
