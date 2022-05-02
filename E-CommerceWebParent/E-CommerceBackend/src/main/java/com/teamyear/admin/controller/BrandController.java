package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.BrandForm;
import com.teamyear.admin.service.BrandService;
import com.teamyear.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ModelAttribute("brandList")
    public List<Brand> brandList() {
        return brandService.findAll();
    }

    @GetMapping("/brand")
    public String brandForm(ModelMap model) {

        model.addAttribute("form", new BrandForm());
        return "admin/ECS-BRN001";
    }

    @PostMapping("/add-brand")
    public String addBrand(@ModelAttribute("form") @Validated BrandForm form, BindingResult br, ModelMap model,
            RedirectAttributes ra) throws IOException {

        if (br.hasErrors()) {
            if (form.getBrandLogo().getOriginalFilename().isEmpty()) {
                model.addAttribute("errorimg", "Brand Logo can't be blank!");
            }
            return "/admin/ECS-BRN001";
        } else if (form.getBrandLogo().getOriginalFilename().isEmpty()) {
            model.addAttribute("errorimg", "Brand Logo can't be blank!");
            return "/admin/ECS-BRN001";
        }

        // brand name check
        List<Brand> bList = brandService.findByBrandName(form.getBrandName());
        if (bList.size() > 0) {
            model.addAttribute("error", "Brand Name already exist!");
            return "/admin/ECS-BRN001";
        }

        Brand brand = new Brand();
        brand.setBrandName(form.getBrandName());
        brand.setBrandDescription(form.getBrandDescription());
        brandService.save(brand);
        brand = brandService.findByBrandName(brand.getBrandName()).get(0);

        // File save
        MultipartFile logo = form.getBrandLogo();
        String logoName = logo.getOriginalFilename().replaceAll(" ", "");
        String dir = "./images/BrandLogo/" + brand.getId() + "/";
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path filePath = path.resolve(logoName);
        InputStream inputStream = logo.getInputStream();
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        brand.setBrandLogo("/images/BrandLogo/" + brand.getId() + "/" + logoName);
        brandService.save(brand);

        ra.addFlashAttribute("success", "Brand name " + brand.getBrandName() + " successfully registered.");
        return "redirect:/admin/brand";
    }

    @GetMapping("/update-brand")
    public String updateBrandForm(@RequestParam("id") Integer id, ModelMap model) {
        Brand brand = brandService.findById(id).get();
        BrandForm brandForm = new BrandForm();
        brandForm.setId(brand.getId());
        brandForm.setBrandName(brand.getBrandName());
        brandForm.setBrandDescription(brand.getBrandDescription());
        model.addAttribute("form", brandForm);
        return "admin/ECS-BRN001";
    }

    @PostMapping("/update-brand")
    public String updateBrand(@RequestParam("id") Integer id, @ModelAttribute("form") @Validated BrandForm form,
            BindingResult br, RedirectAttributes ra, ModelMap model) throws IOException {
        if (br.hasErrors()) {
            return "admin/ECS-BRN001";
        }

        Brand brand = brandService.findById(id).get();

        if (!brand.getBrandName().equals(form.getBrandName())) {
            // brand name check
            List<Brand> bList = brandService.findByBrandName(form.getBrandName());
            if (bList.size() > 0) {
                model.addAttribute("error", "Brand Name already exist!");
                return "/admin/ECS-BRN001";
            }
        }

        // file update
        MultipartFile logo = form.getBrandLogo();
        String logoName = logo.getOriginalFilename().replaceAll(" ", "");

        if (!logoName.isEmpty()) {
            // update path
            String dir = "./images/BrandLogo/" + id + "/";
            Path path = Paths.get(dir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path filepath = path.resolve(logoName);

            // delete path
            String delDir = "." + brand.getBrandLogo();
            Path delPath = Paths.get(delDir);

            InputStream inputStream = logo.getInputStream();
            Files.deleteIfExists(delPath);
            Files.copy(inputStream, filepath, StandardCopyOption.REPLACE_EXISTING);

            brand.setBrandLogo("/images/BrandLogo/" + id + "/" + logoName);
        } else {
            String originalLocation = brand.getBrandLogo();
            String updateLocation = originalLocation.replace(brand.getBrandName(), form.getBrandName());

            brand.setBrandLogo(updateLocation);
        }

        brand.setBrandName(form.getBrandName());
        brand.setBrandDescription(form.getBrandDescription());
        brandService.save(brand);

        ra.addFlashAttribute("success", "Brand update successful.");
        return "redirect:/admin/brand";
    }

    @GetMapping("/delete-brand")
    public String deleteBrand(@RequestParam("id") Integer id, RedirectAttributes ra) throws IOException {
        Brand brand = brandService.findById(id).get();

        // delete path
        String delDir = "./images/BrandLogo/" + brand.getId();
        Path delPath = Paths.get(delDir);
        Files.walk(delPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);

        brandService.deleteById(id);
        ra.addFlashAttribute("success", brand.getBrandName() + " successfully deleted.");
        return "redirect:/admin/brand";
    }

}