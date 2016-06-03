package com.capital.dragon.REPO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.capital.dragon.model.EmploeeBill;


public interface EmploeeBillRepo extends CrudRepository<EmploeeBill, Integer>{
	EmploeeBill findByFileName(String filename);	
	List<EmploeeBill> findByPeriod(String period);
}
