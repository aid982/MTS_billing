package com.capital.dragon.controllers;


import com.capital.dragon.REPO.EmploeeRepo;
import com.capital.dragon.model.Emploee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/emploees")
public class Emploees {
	private EmploeeRepo emploeeRepo;
	@Autowired	
	public Emploees(EmploeeRepo emploeeRepo) {		
		this.emploeeRepo = emploeeRepo;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        List<Emploee> sortedEmploeeList = new ArrayList<>();
        for (Emploee emploee : emploeeRepo.findAll()) {
            sortedEmploeeList.add(emploee);
        }
        Collections.sort(sortedEmploeeList, Comparator.comparing((Emploee e) -> e.getAutoCreated()).reversed()
                .thenComparing((Emploee e) -> e.getName()));


        return new ModelAndView("emploees", "emploees", sortedEmploeeList);

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String saveEmploee(@ModelAttribute Emploee emploee) {
		
		emploeeRepo.save(emploee);
		return "redirect:/emploees";
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public String deleteEmploee(@ModelAttribute Emploee emploee) {		
		emploeeRepo.delete(emploee);
		return "redirect:/emploees";
	}
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ModelAndView getEmploee(@PathVariable("id") Integer id) {		
		return new ModelAndView("Edit/emploee","emploee",emploeeRepo.findOne(id));

	}
	
	

}
