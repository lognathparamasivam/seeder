package com.seeder.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seeder.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select sum(available_amount-financed_amount) from contracts c where user_id = :userId", nativeQuery = true)
	public BigDecimal getAvailableCredit(@Param("userId") Long userId);
}
