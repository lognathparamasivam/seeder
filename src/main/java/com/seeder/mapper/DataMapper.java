package com.seeder.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeder.dto.ContractDTO;
import com.seeder.model.Contract;

@Component
public class DataMapper {

	@Autowired
	ModelMapper modelMapper;
	
	
	public ContractDTO toContractDto(Contract contract) {
        return modelMapper.map(contract, ContractDTO.class);
    }

    public Contract toContractEntity(ContractDTO contractDto) {
        return modelMapper.map(contractDto, Contract.class);
    }
}
