package com.capital.dragon.REPO;

import com.capital.dragon.model.Emploee;
import org.springframework.data.repository.CrudRepository;


public interface EmploeeRepo extends CrudRepository<Emploee, Integer>{
	Emploee findByPhone(String phone);
	Emploee findByEmail(String email);
	Emploee findByName(String name);
	Emploee findByLogin(String login);

}
