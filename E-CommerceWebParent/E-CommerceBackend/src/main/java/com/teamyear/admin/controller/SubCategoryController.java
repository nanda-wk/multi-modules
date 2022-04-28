package com.teamyear.admin.controller;

import java.util.List;

import com.teamyear.admin.formmodel.SubCategoryForm;
import com.teamyear.admin.service.CategoryService;
import com.teamyear.admin.service.SubCategoryService;
import com.teamyear.common.entity.Category;
import com.teamyear.common.entity.SubCategory;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categoryList")
    public List<Category> categoryList() {
        return categoryService.findAll();
    }

    @ModelAttribute("subList")
    public List<SubCategory> subCategoryList() {
        return subCategoryService.findAll();
    }

    @GetMapping("/sub-category")
    public String subCategories(ModelMap model) {

        model.addAttribute("form", new SubCategoryForm());
        return "admin/ECS-SCAT001";
    }

    @PostMapping("/add-sub-category")
    public String addSubCategory(@ModelAttribute("form") @Validated SubCategoryForm form, BindingResult br,
            RedirectAttributes ra, ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-SCAT001";
        }

        List<SubCategory> subList = subCategoryService.findBySubCategoryName(form.getSubCategoryName());
        if (subList.size() != 0) {
            model.addAttribute("error", "Name already exist!");
            return "admin/ECS-SCAT001";
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(form.getSubCategoryName());
        subCategory.setCategory(form.getCategory());
        subCategoryService.save(subCategory);

        ra.addFlashAttribute("success", subCategory.getSubCategoryName() + " successfully registered.");
        return "redirect:/admin/sub-category";
    }

    @GetMapping("/update-sub-category")
    public String updateSubCategoryForm(@RequestParam("id") Integer id, ModelMap model) {
        SubCategory subCategory = subCategoryService.findById(id);
        SubCategoryForm form = new SubCategoryForm();
        form.setId(id);
        form.setSubCategoryName(subCategory.getSubCategoryName());
        form.setCategory(subCategory.getCategory());

        model.addAttribute("form", form);
        return "admin/ECS-SCAT001";
    }

    @PostMapping("/update-sub-category")
    public String updateSubCategory(@RequestParam("id") Integer id,
            @ModelAttribute("form") @Validated SubCategoryForm form, BindingResult br,
            RedirectAttributes ra, ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-SCAT001";
        }

        SubCategory subCategory = subCategoryService.findById(id);

        if (!subCategory.getSubCategoryName().equals(form.getSubCategoryName())) {
            List<SubCategory> subList = subCategoryService.findBySubCategoryName(form.getSubCategoryName());
            if (subList.size() != 0) {
                model.addAttribute("error", "Name already exist!");
                return "admin/ECS-SCAT001";
            }
        }

        subCategory.setSubCategoryName(form.getSubCategoryName());
        subCategory.setCategory(form.getCategory());
        subCategoryService.save(subCategory);

        ra.addFlashAttribute("success", "Update Successful.");
        return "redirect:/admin/sub-category";
    }

    @GetMapping("/delete-sub-category")
    public String deleteSubCategory(@RequestParam("id") Integer id, RedirectAttributes ra) {
        SubCategory subCategory = subCategoryService.findById(id);
        subCategoryService.deleteById(id);
        ra.addFlashAttribute("success", subCategory.getSubCategoryName() + " successfully deleted.");
        return "redirect:/admin/sub-category";
    }

}