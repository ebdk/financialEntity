package com.uade.financialEntity.repositories;

import com.uade.financialEntity.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long> {
	Optional<Customer> findByDni(Integer dni);
}
