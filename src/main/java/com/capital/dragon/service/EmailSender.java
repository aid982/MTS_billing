package com.capital.dragon.service;

import com.capital.dragon.model.Emploee;
import com.capital.dragon.model.EmploeeBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSender {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(EmploeeBill emploeeBill, Emploee emploee) throws Exception {
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setText(emploeeBill.toString());
        helper.setTo(emploeeBill.getEmploee().getEmail());
        helper.setFrom(emploee.getEmail());
        helper.setSubject(emploeeBill.getPeriod() + " cell phone bill for " + emploeeBill.getEmploee().getName() + " " + emploeeBill.getToBePaid() + " UAH");
        helper.addAttachment(emploeeBill.getFileName(), new File(emploeeBill.getFileName()));
        javaMailSender.send(mail);


    }

}
