package com.capital.dragon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.model.EmploeeBill;


@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class ListOfBills {
	private List<EmploeeBill> emploeeBills = new ArrayList<>();
	

	public List<EmploeeBill> getEmploeeBills() {
		return emploeeBills;
	}

	public void setEmploeeBills(List<EmploeeBill> emploeeBills) {
		this.emploeeBills = emploeeBills;
	}
	
	public Integer getPackageCost() {
		return emploeeBills.stream().mapToInt(bill->bill.getPackageCost()).sum();	
	}
	
	public Integer getPackageCostWithTaxes() {
		return emploeeBills.stream().mapToInt(bill->bill.getPackageCostWithTaxes()).sum();	
	}
	
	public Integer getTotalAmount() {
		return emploeeBills.stream().mapToInt(bill->bill.getTotalAmount()).sum();	
	}
	
	public Integer getTotalAmountWithTaxes(){
		return emploeeBills.stream().mapToInt(bill->bill.getTotalAmountWithTaxes()).sum();	
	}
	
	public Integer getTotalLimit(){
		return emploeeBills.stream().mapToInt(bill->bill.getEmploee().getLimitPaidByCompany()).sum();	
	}
	
	public Integer getTotalToBePaid(){
		return emploeeBills.stream().mapToInt(bill->bill.getToBePaid()).sum();	
	}
	
	public Integer getTotalGeneralDiscount(){
		return emploeeBills.stream().mapToInt(bill->bill.getGeneralDiscount()).sum();	
	}
	
	public Integer getTotalRoaming(){
		return emploeeBills.stream().mapToInt(bill->bill.getRoamingCost()).sum();	
	}
	
	
	public void DestributeAmountOfTaxesBeetweenEmploees(EmploeeBillRepo billRepo){
		emploeeBills.stream().mapToInt(bill->bill.getTotalAmountWithTaxes()).sum();	
	}
	
	

}
