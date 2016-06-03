package com.capital.dragon.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class EmploeeBill {		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	public void setId(Integer id) {
		this.id = id;
	}
	private Date data;	
	@ManyToOne
	private Emploee emploee;	
	private Integer packageCost;
	private Integer packageCostWithTaxes;	
	private Integer totalAmount;
	private Integer totalAmountWithTaxes;	
	private Integer roamingCost;	
	private Integer roamingDiscount;	
	private Integer generalDiscount;	
	private Integer toBePaid;	
	private String fileName;
	private String period;
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getPackageCostWithTaxes() {
		return packageCostWithTaxes;
	}
	public void setPackageCostWithTaxes(Integer packageCostWithTaxes) {
		this.packageCostWithTaxes = packageCostWithTaxes;
	}
	public Integer getTotalAmount() {
		if(totalAmount==null) {
			totalAmount = 0;
		}
		return totalAmount;
		
	}
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;		
	}
	public Integer getTotalAmountWithTaxes() {
		return totalAmountWithTaxes;
	}
	public void setTotalAmountWithTaxes(Integer totalAmountWithTaxes) {
		this.totalAmountWithTaxes = totalAmountWithTaxes;
	}	
	
	public Emploee getEmploee() {
		return emploee;
	}
	public void setEmploee(Emploee emploee) {
		this.emploee = emploee;
	}
	// i am storin integer value *100
	public Integer getPackageCost() { 
		return packageCost;
	}
	public void setPackageCost(Integer packageCost) {
		this.packageCost = packageCost;	
		
	}
	

	public Integer getRoamingCost() {
		return roamingCost;
	}
	public void setRoamingCost(Integer roamingCost) {
		this.roamingCost = roamingCost;
	}
	public Integer getRoamingDiscount() {
		return roamingDiscount;
	}
	public void setRoamingDiscount(Integer roamingDiscount) {
		this.roamingDiscount = roamingDiscount;
	}
	public Integer getGeneralDiscount() {
		return generalDiscount;
	}
	public void setGeneralDiscount(Integer generalDiscount) {
		this.generalDiscount = generalDiscount;
	}
	public Integer getToBePaid() {
		return toBePaid;
	}
	public void setToBePaid(Integer toBePaid) {
		this.toBePaid = toBePaid;
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getId() {
		return id;
	}
	
	public void setAllFieldsToZero(){
		this.packageCost =0;
		this.packageCostWithTaxes =0;
		this.totalAmount = 0;
		this.totalAmountWithTaxes = 0;
		this.toBePaid = 0;
		this.roamingCost =0 ;
		this.generalDiscount = 0;
		this.roamingDiscount = 0;
		
	}
	
	
	
	
	
	

}
