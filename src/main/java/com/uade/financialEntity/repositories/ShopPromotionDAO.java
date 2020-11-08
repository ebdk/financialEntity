package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.ShopPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPromotionDAO extends JpaRepository<ShopPromotion, Long> {
}
