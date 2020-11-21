package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.CardSolicitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardSolicitationDAO extends JpaRepository<CardSolicitation, Long> {
}
