package com.capital.dragon.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.capital.dragon.model.EmploeeBill;

public class EmailSender {
	
	public static void sendEmail(EmploeeBill emploeeBill,JavaMailSender javaMailSender) {
		MimeMessage mail = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(emploeeBill.getEmploee().getEmail());			
			helper.setFrom("mts_billing@dragon-capital.com");
			helper.setSubject(emploeeBill.getPeriod()+ " cell phone bill for "+emploeeBill.getEmploee().getName() +" "+emploeeBill.getToBePaid()+" UAH");			
			helper.addAttachment(emploeeBill.getFileName(), new File(emploeeBill.getFileName()));
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
		}
		javaMailSender.send(mail);
	}

}
