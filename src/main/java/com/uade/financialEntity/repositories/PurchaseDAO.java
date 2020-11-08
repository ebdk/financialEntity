package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDAO extends JpaRepository<Purchase, Long> {
}
