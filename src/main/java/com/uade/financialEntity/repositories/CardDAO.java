package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDAO extends JpaRepository<Card, Long> {
}
