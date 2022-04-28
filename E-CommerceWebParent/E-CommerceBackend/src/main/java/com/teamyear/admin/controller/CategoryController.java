package com.teamyear.admin.controller;

import java.util.List;

import com.teamyear.admin.formmodel.CategoryForm;
import com.teamyear.admin.service.CategoryService;
import com.teamyear.common.entity.Category;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("catlist")
    public List<Category> catList() {
        return categoryService.findAll();
    }

    @GetMapping("/category")
    public String addCategoryForm(ModelMap model) {
        List<Category> catlist = categoryService.findAll();
//        model.addAttribute("catlist", catlist);
        model.addAttribute("form", new CategoryForm());
        return "admin/ECS-CAT001";
    }

    @PostMapping("/add-category")
    public String category(@ModelAttribute("form") @Validated CategoryForm form, BindingResult br,
            RedirectAttributes ra, ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-CAT001";
        }

        List<Category> list = categoryService.findByCategoryName(form.getCategoryName());
        if (list.size() != 0) {
            model.addAttribute("error", "Category name already exits!");
            return "admin/ECS-CAT001";
        } else {
            Category c = new Category();
            c.setCategoryName(form.getCategoryName());
            c.setCategoryDescription(form.getCategoryDescritpion());
            categoryService.save(c);
            List<Category> l = categoryService.findByCategoryName(c.getCategoryName());
            if (l.size() > 0) {
                ra.addFlashAttribute("success", "Category successful added");
            }
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/update-category")
    public ModelAndView editCategoryForm(@RequestParam("id") Integer id) {
        Category coption = categoryService.findById(id);
        CategoryForm form = new CategoryForm();
        form.setId(coption.getId());
        form.setCategoryName(coption.getCategoryName());
        form.setCategoryDescritpion(coption.getCategoryDescription());

        return new ModelAndView("admin/ECS-CAT001", "form", form);
    }

    @PostMapping("/update-category")
    public String editCategory(@ModelAttribute("form") @Validated CategoryForm cform, BindingResult br,
            RedirectAttributes ra,
            ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-CAT001";
        }

        Category coption = categoryService.findById(cform.getId());
        coption.setCategoryName(cform.getCategoryName());
        coption.setCategoryDescription(cform.getCategoryDescritpion());

        categoryService.save(coption);

        List<Category> l = categoryService.findByCategoryName(coption.getCategoryName());
        if (l.size() > 0) {
            ra.addFlashAttribute("success", "Category successfully updated");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/delete-category")
    public String deleteCategory(@RequestParam("id") Integer id, RedirectAttributes ra) {
        categoryService.deleteById(id);
        ra.addFlashAttribute("success", "Delete successful");
        return "redirect:/admin/category";
    }

}