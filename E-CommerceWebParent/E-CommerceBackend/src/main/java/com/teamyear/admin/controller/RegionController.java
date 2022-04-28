package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.RegionForm;
import com.teamyear.admin.service.RegionService;
import com.teamyear.common.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ModelAttribute("regionList")
    public List<Region> regionList() {
        return regionService.findAll();
    }

    @GetMapping("/region")
    public String region(ModelMap model) {

        model.addAttribute("form", new RegionForm());

        return "admin/ECS-REG001";
    }

    @PostMapping("/add-region")
    public String addRegion(@ModelAttribute("form") @Validated RegionForm form, BindingResult br,
                            RedirectAttributes ra, ModelMap moedl) {
        if (br.hasErrors()){
            return "admin/ECS-REG001";
        } else if (regionService.checkRegionName(form.getName())){
            moedl.addAttribute("error", "Region already exist.");
            return "admin/ECS-REG001";
        }

        Region region = new Region();
        region.setName(form.getName());
        regionService.save(region);

        ra.addFlashAttribute("success", "Region successfully registered.");
        return "redirect:/admin/region";
    }

    @GetMapping("/update-region")
    public String updateRegionForm(@RequestParam("id") Integer id, ModelMap model) {
        Region region = regionService.findById(id).get();
        RegionForm form = new RegionForm();
        form.setId(region.getId());
        form.setName(region.getName());

        model.addAttribute("form", form);
        return "admin/ECS-REG001";
    }

    @PostMapping("/update-region")
    public String updateRegion(@RequestParam("id") Integer id, @ModelAttribute("form") @Validated RegionForm form,
                               BindingResult br, RedirectAttributes ra, ModelMap model){
        if(br.hasErrors()){
            return "admin/ECS-REG001";
        }

        Region region = regionService.findById(id).get();
        if (!region.getName().equals(form.getName())){
            if(regionService.checkRegionName(form.getName())){
                model.addAttribute("error", "Region already exist.");
                return "admin/ECS-REG001";
            }
        }

        region.setName(form.getName());
        regionService.save(region);

        ra.addFlashAttribute("success", "Update successful.");
        return "redirect:/admin/region";
    }

    @GetMapping("/delete-region")
    public String deleteRegion(@RequestParam("id") Integer id, RedirectAttributes ra) {
        regionService.deleteById(id);
        ra.addFlashAttribute("success", "Delete successful.");
        return "redirect:/admin/region";
    }
}