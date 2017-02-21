package com.capital.dragon.controllers;

import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.Security.CurrentUser;
import com.capital.dragon.model.Emploee;
import com.capital.dragon.model.EmploeeBill;
import com.capital.dragon.service.EmailSender;
import com.capital.dragon.service.ListOfBills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/bills")
public class Bills {
    private EmploeeBillRepo emploeeBillRepo;
    private ListOfBills listOfBills;
    private EmailSender javaMailSender;

    @Autowired
    public Bills(EmploeeBillRepo emploeeBillRepo, ListOfBills listOfBills, EmailSender javaMailSender) {
        this.emploeeBillRepo = emploeeBillRepo;
        this.listOfBills = listOfBills;
        this.javaMailSender = javaMailSender;
    }

    @ResponseBody
    @RequestMapping(value = "/api/{data}", method = RequestMethod.POST)
    public List<EmploeeBill> getBillsByPeriod(@PathVariable("data") String date) {

        return emploeeBillRepo.findByPeriod(date).stream().filter(emploeeBill -> emploeeBill.getToBePaid() > 0).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(name = "date", required = false) String date) {
        Map<String, Object> model = new HashMap<>();
        //2017-01
        if (date != null) {
            listOfBills.setEmploeeBills(emploeeBillRepo.findByPeriod(date));
        } else {
            listOfBills.setEmploeeBills(new ArrayList<>());

        }

        Collections.sort(listOfBills.getEmploeeBills(), Comparator.comparing((EmploeeBill e) -> e.getEmploee().getAutoCreated()).reversed()
                .thenComparing((EmploeeBill e) -> e.getEmploee().getName()));


        model.put("chosedPeriod", date);
        model.put("bills", listOfBills);

        return new ModelAndView("bills", model);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getBill(@PathVariable("id") Integer id) {
        System.out.println(emploeeBillRepo.findOne(id));
        return new ModelAndView("Edit/bill", "emploeeBill", emploeeBillRepo.findOne(id));

    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String sendEMAIL(@RequestParam(name = "id", required = false) Integer billID, @CurrentUser Emploee user, RedirectAttributes redirectAttributes) {
        try {
            if (billID != null) {
                javaMailSender.sendEmail(emploeeBillRepo.findOne(billID), user);
            } else {
                List<EmploeeBill> emploeeBills = listOfBills.getEmploeeBills();
                for (EmploeeBill emploeeBill : emploeeBills) {
                    javaMailSender.sendEmail(emploeeBill, user);
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "There is some error seding emails" + e.getMessage() + " " + e.getCause());

        }

        return "redirect:/bills";


    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteBills() {
        List<EmploeeBill> list = listOfBills.getEmploeeBills();
        for (EmploeeBill emploeeBill : list) {
            emploeeBillRepo.delete(emploeeBill);
        }

        return "redirect:/bills";


    }


}
