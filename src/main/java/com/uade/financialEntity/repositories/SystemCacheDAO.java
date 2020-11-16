package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.SystemCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemCacheDAO extends JpaRepository<SystemCache, Long> {
}
