package com.capital.dragon.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.repository.cdi.Eager;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Emploee {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Integer id;		
	private String login;
	private String name;	
	private String phone;
	@Email(message = "Please provide a valid email address.")
	@NotEmpty(message = "Email is required.")
	private String email;
	private String department;	
	private Integer limitPaidByCompany = 0;
	private Boolean packageCostCovered = false;
	
	private String password;
	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Emploee() {
		
	}
	
	public Emploee(Emploee emploee) {
		this.id = emploee.id;
		this.name = emploee.name;
		this.email  = emploee.email;
		this.password = emploee.password;
		this.department = emploee.department;
		this.limitPaidByCompany = emploee.limitPaidByCompany;
		this.phone = emploee.phone;		
		
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getPackageCostCovered() {
		return packageCostCovered;
	}
	public void setPackageCostCovered(Boolean packageCostCovered) {
		this.packageCostCovered = packageCostCovered;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@ManyToMany
	@JoinTable
	@JsonIgnore	
	private List<Role> roles = new  ArrayList<>();
		
	@Override
	public String toString() {
		return name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Integer getLimitPaidByCompany() {
		return limitPaidByCompany;
	}
	public void setLimitPaidByCompany(Integer limitPaidByCompany) {
		this.limitPaidByCompany = limitPaidByCompany;
	}
	
	public String[] getRolesAsStrings(){
		String[] arrayRoles = new String[roles.size()];
		for (int i = 0; i < roles.size(); i++) {
			arrayRoles[i] = roles.get(i).toString();		
		}		
		return arrayRoles;
		
	}
	
	
	
	
	
	
	
	

}
