package com.teamyear.admin.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import com.teamyear.admin.formmodel.AnnouncementForm;
import com.teamyear.admin.service.AnnouncementService;
import com.teamyear.admin.util.FileUpload;
import com.teamyear.common.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AnnouncementController {

	@Autowired
	AnnouncementService anmService;

	@ModelAttribute("anmList")
	public List<Announcement> anouAnnouncementList() {
		return anmService.findAll();
	}

	@GetMapping("/announcement")
    public String announcementForm(ModelMap model) {

        model.addAttribute("form", new AnnouncementForm());
        return "admin/ECS-ANM001";
    }

	 @PostMapping("/add-announcement")
	    public String addAnnouncement(@ModelAttribute("form") @Validated AnnouncementForm form, BindingResult br, ModelMap model,
	            RedirectAttributes ra) throws IOException {

	        List<Announcement> anmList = anmService.findAll();
	        model.addAttribute("anmList", anmList);

	        if (br.hasErrors()) {
	            if (form.getAnmImage().getOriginalFilename().isEmpty()) {
	                model.addAttribute("errorimg", "Announcement Image can't be blank!");
	            }
	            return "/admin/ECS-ANM001";
	        } else if (form.getAnmImage().getOriginalFilename().isEmpty()) {
	            model.addAttribute("errorimg", "Announcement Image can't be blank!");
	            return "/admin/ECS-ANM001";
	        }

	        // brand name check
	        List<Announcement> aList = anmService.findByAnnouncementName(form.getAnmName());
	        if (aList.size() > 0) {
	            model.addAttribute("error", "Announcement Name already exist!");
	            return "/admin/ECS-ANM001";
	        }

	        Announcement anm = new Announcement();
	        anm.setAnmName(form.getAnmName());
	        anmService.save(anm);
	        anm = anmService.findByAnnouncementName(anm.getAnmName()).get(0);

	        // File save
	        MultipartFile image = form.getAnmImage();
	        String imgName = image.getOriginalFilename().replaceAll(" ", "");
	        String dir = "./images/AnnouncementImage/" + anm.getId() + "/";

		 	FileUpload.saveImage(image, imgName, dir);
	        anm.setAnmImage("/images/AnnouncementImage/" + anm.getId() + "/" + imgName);
	        anmService.save(anm);

	        ra.addFlashAttribute("success", "Announcement name " + anm.getAnmName() + " successfully added.");
	        return "redirect:/admin/announcement";
	    }

	    @GetMapping("/update-announcement")
	    public String updateAnnouncement(@RequestParam("id") Integer id, ModelMap model) {
	        Announcement anm = anmService.findById(id);
	        AnnouncementForm anmForm = new AnnouncementForm();
	        anmForm.setId(anm.getId());
	        anmForm.setAnmName(anm.getAnmName());
	        model.addAttribute("form", anmForm);
	        return "admin/ECS-ANM001";
	    }

	    @PostMapping("/update-announcement")
	    public String updateAnnouncement(@RequestParam("id") Integer id, @ModelAttribute("form") @Validated AnnouncementForm form,
	            BindingResult br, RedirectAttributes ra, ModelMap model) throws IOException {
	        if (br.hasErrors()) {
	            return "admin/ECS-ANM001";
	        }

	        Announcement anm = anmService.findById(id);

	        if (!anm.getAnmName().equals(form.getAnmName())) {
	            // brand name check
	            List<Announcement> aList = anmService.findByAnnouncementName(form.getAnmName());
	            if (aList.size() > 0) {
	                model.addAttribute("error", "Announcement Name already exist!");
	                return "/admin/ECS-ANM001";
	            }
	        }

	        // file update
	        MultipartFile logo = form.getAnmImage();
	        String logoName = logo.getOriginalFilename().replaceAll(" ", "");

	        if (!logoName.isEmpty()) {
	            // update path
	            String dir = "./images/AnnouncementImage/" + id + "/";

	            FileUpload.deleteImage(anm.getAnmImage());
	            FileUpload.saveImage(logo, logoName, dir);

	            anm.setAnmImage("/images/AnnouncementImage/" + id + "/" + logoName);
	        } else {
	            String originalLocation = anm.getAnmImage();
	            String updateLocation = originalLocation.replace(anm.getAnmName(), form.getAnmName());

	            anm.setAnmImage(updateLocation);
	        }

	        anm.setAnmName(form.getAnmName());
	        anmService.save(anm);

	        ra.addFlashAttribute("success", "Announcement update successful.");
	        return "redirect:/admin/announcement";
	    }

	    @GetMapping("/delete-announcement")
	    public String deleteAnnouncement(@RequestParam("id") Integer id, RedirectAttributes ra) throws IOException {
	        Announcement anm = anmService.findById(id);

	        // delete path
	        String delDir = "./images/AnnouncementImage/" + anm.getId();
	        Path delPath = Paths.get(delDir);
	        Files.walk(delPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);

	        anmService.deleteById(id);
	        ra.addFlashAttribute("success", anm.getAnmName() + " successfully deleted.");
	        return "redirect:/admin/announcement";
	    }

}