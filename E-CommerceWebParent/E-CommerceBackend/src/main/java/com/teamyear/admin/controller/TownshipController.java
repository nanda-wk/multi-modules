package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.TownshipForm;
import com.teamyear.admin.service.CityService;
import com.teamyear.admin.service.TownshipService;
import com.teamyear.common.entity.City;
import com.teamyear.common.entity.Township;
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
public class TownshipController {

    private final String DEFAULT_ROUTE = "admin/ECS-TWN001";
    private final String REDIRECT_ROUTE = "redirect:/admin/township";

    @Autowired
    private TownshipService townshipService;

    @Autowired
    private CityService cityService;

    @ModelAttribute("townshipList")
    public List<Township> townshipList() {
        return townshipService.findAll();
    }

    @ModelAttribute("cityList")
    public List<City> cityList() {
        return cityService.findAll();
    }

    @GetMapping("/township")
    public String township(ModelMap model) {
        model.addAttribute("form", new TownshipForm());
        return DEFAULT_ROUTE;
    }

    @PostMapping("/add-township")
    public String addTownship(@ModelAttribute("form") @Validated TownshipForm form,
                              BindingResult br, RedirectAttributes ra, ModelMap model) {
        if(br.hasErrors()){
            return  DEFAULT_ROUTE;
        } else if(townshipService.checkTownshipName(form.getName())){
            model.addAttribute("error", "Township already exist.");
            return DEFAULT_ROUTE;
        }

        Township township = new Township();
        township.setName(form.getName());
        township.setCity(form.getCity());
        townshipService.save(township);

        ra.addFlashAttribute("success", "Township successfully registered");
        return REDIRECT_ROUTE;
    }

    @GetMapping("/update-township")
    public String updateTownshipForm(@RequestParam("id")Integer id, ModelMap model) {
        Township township = townshipService.findById(id).get();
        TownshipForm form = new TownshipForm();
        form.setId(township.getId());
        form.setName(township.getName());
        form.setCity(township.getCity());

        model.addAttribute("form", form);
        return DEFAULT_ROUTE;
    }

    @PostMapping("/update-township")
    public String updateTownship(@RequestParam("id") Integer id, @ModelAttribute("form") @Validated TownshipForm form,
                                 BindingResult br, RedirectAttributes ra, ModelMap model) {
        if(br.hasErrors()){
            return DEFAULT_ROUTE;
        }

        Township township = townshipService.findById(id).get();
        if(!township.getName().equals(form.getName())) {
            if(townshipService.checkTownshipName(form.getName())){
                model.addAttribute("error", "Township already exist.");
                return DEFAULT_ROUTE;
            }
        }

        township.setName(form.getName());
        township.setCity(form.getCity());
        townshipService.save(township);

        ra.addFlashAttribute("success", "Update successful.");
        return REDIRECT_ROUTE;
    }

    @GetMapping("/delete-township")
    public String deleteTownship(@RequestParam("id") Integer id, RedirectAttributes ra) {
        townshipService.deleteById(id);
        ra.addFlashAttribute("success", "Delete successful.");
        return REDIRECT_ROUTE;
    }
}