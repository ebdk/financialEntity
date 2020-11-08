package com.uade.financialEntity.repositories;

@org.springframework.stereotype.Repository
public interface UserDAO extends org.springframework.data.jpa.repository.JpaRepository<com.uade.financialEntity.models.User, Long> {
    java.util.Optional<com.uade.financialEntity.models.User> findByUserName(String userName);
    java.util.List<com.uade.financialEntity.models.User> findByRank(com.uade.financialEntity.models.User.UserRank rank);
}
