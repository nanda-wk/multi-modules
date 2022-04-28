package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.CityForm;
import com.teamyear.admin.service.CityService;
import com.teamyear.admin.service.RegionService;
import com.teamyear.common.entity.City;
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
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @ModelAttribute("regionList")
    public List<Region> regionList() {
        return regionService.findAll();
    }

    @ModelAttribute("cityList")
    public List<City> cityList() {
        return cityService.findAll();
    }

    @GetMapping("/city")
    public String city(ModelMap model) {
        model.addAttribute("form", new CityForm());
        return "admin/ECS-CITY001";
    }

    @PostMapping("/add-city")
    public String addCity(@ModelAttribute("form") @Validated CityForm form,
                          BindingResult br, RedirectAttributes ra, ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-CITY001";
        } else if (cityService.checkCityName(form.getName())) {
            model.addAttribute("error", "City already exist.");
            return "admin/ECS-CITY001";
        }

        City city = new City();
        city.setName(form.getName());
        city.setRegion(form.getRegion());
        cityService.save(city);

        ra.addFlashAttribute("success", "City successfully registered.");
        return "redirect:/admin/city";
    }

    @GetMapping("/update-city")
    public String updateCityForm(@RequestParam("id") Integer id, ModelMap model) {
        City city = cityService.findById(id).get();
        CityForm form = new CityForm();

        form.setId(city.getId());
        form.setName(city.getName());
        form.setRegion(city.getRegion());

        model.addAttribute("form", form);
        return "admin/ECS-CITY001";
    }

    @PostMapping("/update-city")
    public String updateCity(@RequestParam("id") Integer id, @ModelAttribute("form") @Validated CityForm form,
                             BindingResult br, RedirectAttributes ra, ModelMap model) {
        if(br.hasErrors()){
            return "admin/ECS-CITY001";
        }

        City city = cityService.findById(id).get();
        if(!city.getName().equals(form.getName())){
            if (cityService.checkCityName(form.getName())){
                model.addAttribute("error", "City already exist.");
                return "admin/ECS-CITY001";
            }
        }

        city.setName(form.getName());
        city.setRegion(form.getRegion());
        cityService.save(city);

        ra.addFlashAttribute("success", "Update successful.");
        return "redirect:/admin/city";
    }

    @GetMapping("/delete-city")
    public String deleteCity(@RequestParam("id") Integer id, RedirectAttributes ra){
        cityService.deleteById(id);

        ra.addFlashAttribute("success", "Delete successful.");
        return "redirect:/admin/city";
    }
}