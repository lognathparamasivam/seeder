package com.seeder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seeder.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{

}
