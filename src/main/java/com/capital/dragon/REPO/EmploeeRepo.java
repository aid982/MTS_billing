package com.capital.dragon.REPO;

import org.springframework.data.repository.CrudRepository;
import com.capital.dragon.model.Emploee;


public interface EmploeeRepo extends CrudRepository<Emploee, Integer>{
	Emploee findByPhone(String phone);
	Emploee findByEmail(String email);
	Emploee findByName(String name);
	Emploee findByLogin(String login);

}
