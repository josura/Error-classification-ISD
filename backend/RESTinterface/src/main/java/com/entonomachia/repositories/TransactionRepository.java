package com.entonomachia.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entonomachia.domains.TransactionStatus;

@Repository
public class TransactionRepository implements CrudRepository<TransactionStatus,String>{

	@Override
	public <S extends TransactionStatus> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends TransactionStatus> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TransactionStatus> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<TransactionStatus> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TransactionStatus> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TransactionStatus entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends TransactionStatus> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}
	// automatically getting a complete set of persistence methods that perform CRUD functionality.
}
