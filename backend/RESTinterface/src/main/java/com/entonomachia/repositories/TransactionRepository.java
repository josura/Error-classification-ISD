package com.entonomachia.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entonomachia.domains.TransactionStatus;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionStatus,String>{
	
}
