package com.capital.dragon;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestMatcher {
	@Test
	public void contextLoads() throws Exception {
		//String regex = "(.*)(Контракт №)(.*)";
		//System.out.println(new SimpleDateFormat("MMM",Locale.US).format(Calendar.getInstance().getTime()));
		
		
		String regex = "(\\d{2}.\\d{2}.\\d{4})"; // Begining information about
		String comp ="     Розрахунковий період: 01.04.2016 - 30.04.2016 ";
		System.out.println("IT matches !!! "+comp.matches(regex));
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(comp);		 
		 if(matcher.find()) {			 	         
			 String billDate = matcher.group();
			 System.out.println(billDate);
			 Date date = formatter.parse(billDate);
				System.out.println(date);
				System.out.println(formatter.format(date));
			 
	         
	      }
	}

}
