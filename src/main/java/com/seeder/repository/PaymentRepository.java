package com.seeder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seeder.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByUsers_Id(Long id);

}
