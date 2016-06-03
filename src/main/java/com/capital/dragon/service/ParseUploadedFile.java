package com.capital.dragon.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capital.dragon.MtsBillingApplication;
import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.REPO.EmploeeRepo;
import com.capital.dragon.model.Emploee;
import com.capital.dragon.model.EmploeeBill;

import scala.annotation.meta.setter;

@Service
public class ParseUploadedFile {
	static private double TAX = 0.275;
	
	public static void saveBill(EmploeeRepo emploeeRepo, EmploeeBillRepo emploeeBillRepo,BufferedWriter bufferedWriter,EmploeeBill emploeeBill,Emploee emploee ) throws Exception {
		
		if (bufferedWriter != null) {
			bufferedWriter.flush();
		}
		try {
			if (emploee != null) {

				emploeeRepo.save(emploee);

			}

			if (emploeeBill != null)					{
				Integer toBePaid=0;
				if(emploeeBill.getEmploee().getPackageCostCovered()) {
					toBePaid = emploeeBill.getTotalAmountWithTaxes()-emploeeBill.getPackageCostWithTaxes();
												
				} else {
					toBePaid  =emploeeBill.getTotalAmountWithTaxes()-emploeeBill.getEmploee().getLimitPaidByCompany();							
				}
				if(toBePaid<0) {
					toBePaid = 0;								
				}
				emploeeBill.setToBePaid(toBePaid);
				emploeeBillRepo.save(emploeeBill);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	public static void parseEmploeesBills(File file, EmploeeRepo emploeeRepo, EmploeeBillRepo emploeeBillRepo) throws Exception {
		String line = "";
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "Windows-1251"));
		BufferedWriter bufferedWriter = null;
		Pattern pattern;
		Matcher matcher;
		Emploee emploee = null;
		EmploeeBill emploeeBill = null;
		int index=0;
		Date dateOfBill = Calendar.getInstance().getTime();

		while ((line = bufferedReader.readLine()) != null) {
			String regex = "(.*)(Розрахунковий період:)(.*)";
			if (line.matches(regex)) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");		
				regex = "(\\d{2}.\\d{2}.\\d{4})";  // find out for what period our bills are formed				
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(line);				
				if (matcher.find()) { // finding the firts date
					 dateOfBill  = formatter.parse(matcher.group());
					
				}
				
				
				
			}			
			
			regex = "(.*)(Контракт №)(.*)"; // Begining information about
													// new bill	
			if (line.matches(regex)) {
				// Save previous bill
				saveBill(emploeeRepo, emploeeBillRepo, bufferedWriter, emploeeBill, emploee);				
				index++;
				System.out.println(index+ " " +line);
				
				regex = "(380\\d{9})"; // Phone number pattern;
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(line);
				String phoneNumber = "";
				if (matcher.find()) { // finding phone number
					phoneNumber = matcher.group();
				}	
				
				emploee = emploeeRepo.findByPhone(phoneNumber);
				if (emploee == null) {
					emploee = new Emploee();
					emploee.setPhone(phoneNumber);
					emploee.setName("Unknown user");
				}
				// Create new file
				String currentPeriod = new SimpleDateFormat("YYYY-MM", Locale.US)
						.format(dateOfBill);
				String filePath = MtsBillingApplication.WORKING_DIR + "/" + emploee.getPhone() + "_" + currentPeriod + ".csv";
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"Windows-1251"));

				emploeeBill = emploeeBillRepo.findByFileName(filePath);
				if (emploeeBill == null) {
					emploeeBill = new EmploeeBill();
					emploeeBill.setData(new Date());
					emploeeBill.setFileName(filePath);
					
				}
				emploeeBill.setEmploee(emploee);
				emploeeBill.setPeriod(currentPeriod);
				emploeeBill.setAllFieldsToZero();
				

			}
			if (emploeeBill != null) {
				if (line.matches("(.*)(ВАРТІСТЬ ПАКЕТА/ЩОМІСЯЧНА ПЛАТА:)(.*)")) {
					regex = "(\\d+)(.)(\\d+)"; // sum pattern;
					emploeeBill.setPackageCost(getValueFromParsedString(regex, line,false));
					emploeeBill.setPackageCostWithTaxes(getValueFromParsedString(regex, line,true));
				}
				if (line.matches("(.*)(ЗАГАЛОМ ЗА КОНТРАКТОМ \\(БЕЗ ПДВ ТА ПФ\\):)(.*)")) {
					regex = "(\\d+)(.)(\\d+)"; // sum pattern;
					emploeeBill.setTotalAmount(getValueFromParsedString(regex, line,false));
					emploeeBill.setTotalAmountWithTaxes(getValueFromParsedString(regex, line,true));
				}

				if (line.matches("(.*)(Знижка на суму особового рахунку)(.*)")) {
					regex = "(\\d+)(.)(\\d+)"; // sum pattern;
					emploeeBill.setGeneralDiscount(getValueFromParsedString(regex, line,false));
				}

				if (line.matches("(.*)(ПОСЛУГИ МІЖНАРОДНОГО РОУМІНГУ)(.*)")) {
					regex = "(\\d+)(.)(\\d+)"; // sum pattern;
					emploeeBill.setRoamingCost(getValueFromParsedString(regex, line,false));
				}
			}
			if (bufferedWriter != null) {
				bufferedWriter.newLine();
				bufferedWriter.write(line);
			}

		}
		// Save previous bill (last in file)
		saveBill(emploeeRepo, emploeeBillRepo, bufferedWriter, emploeeBill, emploee);
		bufferedReader.close();
		bufferedWriter.close();

	}

	public static Integer getValueFromParsedString(String regex, String line,Boolean applyTaxes) {
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(line);
		Double value_double;
		Integer value = 0;
		while (matcher.find()) {
			
			if(applyTaxes){
				value_double = Double.parseDouble(matcher.group());
				value_double = Math.round((value_double + value_double*ParseUploadedFile.TAX)*100.00)/1.00;					
				
			} else
			{
				value_double = Double.parseDouble(matcher.group()) * 100;
			}			
			value = value_double.intValue();
		}
		return value;
	}
	
	public static void parseEmploeesList(File file, EmploeeRepo emploeeRepo) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
		List<Emploee> list = bufferedReader.lines().map(ParseUploadedFile::parseEmploee).collect(Collectors.toList());
		for (Emploee emploee : list) {
			saveEmploee(emploee, emploeeRepo);			
		}
		
	}
	
	public static Emploee parseEmploee(String line){
		String[] emplStr = line.split(";");		
		Emploee emploee = new  Emploee();
		emploee.setPhone("380"+emplStr[1]);
		emploee.setDepartment(emplStr[2]);
		emploee.setName(emplStr[3]);
		emploee.setEmail(emplStr[4]);
		try {
			Double limit = Double.parseDouble(emplStr[6])*8;		
			emploee.setLimitPaidByCompany(limit.intValue());
		
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(emploee);
		return emploee;
	}
	
	public static Emploee saveEmploee(Emploee emploee,EmploeeRepo emploeeRepo){
		Emploee emploeSave =emploeeRepo.findByPhone(emploee.getPhone());
		if(emploeSave!=null) {
			emploeSave.setDepartment(emploee.getDepartment());
			emploeSave.setEmail(emploee.getEmail());
			emploeSave.setName(emploee.getName());
			emploeSave.setLimitPaidByCompany(emploee.getLimitPaidByCompany());
			emploeSave.setPackageCostCovered(emploee.getPackageCostCovered());
			emploeeRepo.save(emploeSave);
		} else {
			emploeeRepo.save(emploee);			
		}		
		return emploee;
	}
	
	
}
