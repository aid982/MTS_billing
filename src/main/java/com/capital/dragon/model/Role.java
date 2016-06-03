package com.capital.dragon.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role  {	
	@Id
	private String id;
	
	@ManyToMany(mappedBy="roles")
	private List<Emploee> emploees = new ArrayList<>();		
	
	public List<Emploee> getEmploees() {
		return emploees;
	}
	public void setEmploees(List<Emploee> emploees) {
		this.emploees = emploees;
	}
	public Role() {
	
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
