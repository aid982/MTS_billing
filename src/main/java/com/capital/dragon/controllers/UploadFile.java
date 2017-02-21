package com.capital.dragon.controllers;

import com.capital.dragon.MtsBillingApplication;
import com.capital.dragon.service.ParseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UploadFile {
    private ParseService parseService;

    public UploadFile(ParseService parseService) {
        this.parseService = parseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String provideUploadInfo(Model model) {
        File rootFolder = new File(MtsBillingApplication.UPLOAD_DIR);
        List<String> fileNames = Arrays.stream(rootFolder.listFiles()).map(f -> f.getName())
                .collect(Collectors.toList());

        model.addAttribute("files",
                Arrays.stream(rootFolder.listFiles()).sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
                        .map(f -> f.getName()).collect(Collectors.toList()));

        return "uploadForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        File uploadedFile = new File(MtsBillingApplication.UPLOAD_DIR + "/" + file.getOriginalFilename());
        if (!file.isEmpty()) {

            try {

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + uploadedFile.getName() + "!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "You failed to upload " + uploadedFile.getName() + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message",
                    "You failed to upload " + uploadedFile.getName() + " because the file was empty");
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/createBills", method = RequestMethod.POST)
    public String createBills(RedirectAttributes redirectAttributes) {
        File rootFolder = new File(MtsBillingApplication.UPLOAD_DIR);
        List<File> files = Arrays.stream(rootFolder.listFiles()).collect(Collectors.toList());
        for (File file : files) {
            try {
                parseService.parseEmploeesBills(file);
                //ParseUploadedFile.parseEmploeesList(file, emploeeRepo);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "There is some error processing the bills ");

            }
        }


        redirectAttributes.addFlashAttribute("message", "All done ");
        return "redirect:/";
    }


    @RequestMapping(path = "/createEmploees", method = RequestMethod.POST)
    public String createEmploees(RedirectAttributes redirectAttributes) {
        File rootFolder = new File(MtsBillingApplication.UPLOAD_DIR);
        List<File> files = Arrays.stream(rootFolder.listFiles()).collect(Collectors.toList());
        for (File file : files) {
            try {
                parseService.parseEmploeesList(file);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "There is some error processing the bills ");

            }
        }


        redirectAttributes.addFlashAttribute("message", "All done ");
        return "redirect:/";
    }

    @RequestMapping(path = "/deleteFiles", method = RequestMethod.POST)
    public String deleteFiles(RedirectAttributes redirectAttributes) {
        File rootFolder = new File(MtsBillingApplication.UPLOAD_DIR);
        List<File> files = Arrays.stream(rootFolder.listFiles()).collect(Collectors.toList());
        for (File file : files) {
            try {
                file.delete();
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "There is some error processing the bills ");

            }
        }


        redirectAttributes.addFlashAttribute("message", "All done ");
        return "redirect:/";
    }


}
