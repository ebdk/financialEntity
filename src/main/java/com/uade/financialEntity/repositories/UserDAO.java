package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String userName);
}
