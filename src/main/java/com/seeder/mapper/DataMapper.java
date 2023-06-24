package com.seeder.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeder.dto.CashKickDTO;
import com.seeder.dto.ContractDTO;
import com.seeder.dto.UserDTO;
import com.seeder.model.CashKick;
import com.seeder.model.Contract;
import com.seeder.model.User;

@Component
public class DataMapper {

	@Autowired
	ModelMapper modelMapper;
	

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public ContractDTO toContractDto(Contract contract) {
		return modelMapper.map(contract, ContractDTO.class);
	}

	public Contract toContractEntity(ContractDTO contractDto) {
		return modelMapper.map(contractDto, Contract.class);
	}

	public CashKick toCashKickEntity(CashKickDTO cashKickDto) {
		return modelMapper.map(cashKickDto, CashKick.class);
	}

	public CashKickDTO toCashKickDto(CashKick cashKick) {
		return modelMapper.map(cashKick, CashKickDTO.class);
	}

	public User toUserEntity(UserDTO userDto) {
		return modelMapper.map(userDto, User.class);
	}

	public UserDTO toUserDto(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
}
