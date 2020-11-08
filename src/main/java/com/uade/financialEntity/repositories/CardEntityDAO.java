package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardEntityDAO extends JpaRepository<CardEntity, Long> {
	Optional<CardEntity> findByName(String name);
}
