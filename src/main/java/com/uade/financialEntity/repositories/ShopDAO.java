package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDAO extends JpaRepository<Shop, Long> {
}
