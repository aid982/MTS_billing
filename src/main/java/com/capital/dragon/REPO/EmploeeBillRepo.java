package com.capital.dragon.REPO;

import com.capital.dragon.model.EmploeeBill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmploeeBillRepo extends CrudRepository<EmploeeBill, Integer>{
	EmploeeBill findByFileName(String filename);	
	List<EmploeeBill> findByPeriod(String period);
}
