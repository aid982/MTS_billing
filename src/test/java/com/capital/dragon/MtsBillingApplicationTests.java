package com.capital.dragon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MtsBillingApplicationTests {

	@Test
	public void contextLoads() {
		String regex = "(.*)(Контракт №)(.*)";
		//String regex = "(.*)[0-9]+(.*)";
		String comp ="Контракт № 395380577833  Номер телефону: 380501071682";
		System.out.println("IT matches !!! "+comp.matches(regex));
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(comp);
		int count = 0;
		 while(matcher.find()) {
	         count++;
	         System.out.println(matcher.group());
	         System.out.println("Match number "+count);
	         System.out.println("start(): "+matcher.start());
	         System.out.println("end(): "+matcher.end());
	      }
	}

}
