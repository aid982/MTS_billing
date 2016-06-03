package com.capital.dragon.controllers;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.REPO.EmploeeRepo;
import com.capital.dragon.model.Emploee;
import com.capital.dragon.model.EmploeeBill;
import com.capital.dragon.service.EmailSender;
import com.capital.dragon.service.ListOfBills;



@Controller
@RequestMapping("/bills")
public class Bills {	
	private EmploeeBillRepo emploeeBillRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ListOfBills listOfBills;
	
	@Autowired
	public Bills(EmploeeBillRepo emploeeBillRepo) {	
		this.emploeeBillRepo = emploeeBillRepo;
	}
	
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(name = "date",required = false) String date) {
		Map<String, Object> model= new HashMap<>();		
		if(date!=null)		{		
			listOfBills.setEmploeeBills(emploeeBillRepo.findByPeriod(date));
		}
		model.put("chosedPeriod",date);
		model.put("bills", listOfBills);
		return new ModelAndView("bills",model);

	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ModelAndView getBill(@PathVariable("id") Integer id) {
		System.out.println(emploeeBillRepo.findOne(id));
		return new ModelAndView("Edit/bill","emploeeBill",emploeeBillRepo.findOne(id));

	}
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public String sendEMAIL(@RequestParam(name = "id",required = false) Integer billID) {
		if(billID!=null){
			EmailSender.sendEmail(emploeeBillRepo.findOne(billID), javaMailSender);			
		} else
		{
			List<EmploeeBill> emploeeBills = listOfBills.getEmploeeBills();
			for (EmploeeBill emploeeBill : emploeeBills) {
				EmailSender.sendEmail(emploeeBill, javaMailSender);								
			}						
		}	
		
		return "redirect:/bills";
		

	}
	
	
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	public String deleteBills() {
		List<EmploeeBill> list = listOfBills.getEmploeeBills();
		for (EmploeeBill emploeeBill : list) {
			emploeeBillRepo.delete(emploeeBill);			
		}
			
		return "redirect:/bills";
		

	}
	
	
	
	
	
	
	
	
	

}
