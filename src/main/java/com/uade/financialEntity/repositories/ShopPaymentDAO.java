package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.ShopPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPaymentDAO extends JpaRepository<ShopPayment, Long> {
}
