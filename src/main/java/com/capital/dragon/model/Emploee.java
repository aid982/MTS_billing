package com.capital.dragon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Boolean isAdmin = false;
    private Boolean autoCreated = false;
    @ManyToMany
    @JoinTable
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

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

    public Emploee(String name, String phone, String email, Boolean autoCreated) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.autoCreated = autoCreated;

    }

    public Boolean getAutoCreated() {
        return (autoCreated == null ? false : autoCreated);
    }

    public void setAutoCreated(Boolean autoCreated) {
        this.autoCreated = autoCreated;
    }

    public Boolean getAdmin() {
        return (isAdmin == null ? false : isAdmin);
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
