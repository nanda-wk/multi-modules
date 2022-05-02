package com.teamyear.admin.controller;

import java.util.List;

import com.teamyear.admin.formmodel.DiscountForm;
import com.teamyear.admin.service.DiscountService;
import com.teamyear.common.entity.Discount;
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
public class DiscountController {

	@Autowired
    DiscountService discountService;

	@GetMapping("/discount")
	public String discountForm(ModelMap model) {
		List<Discount> dislist = discountService.findAll();
		model.addAttribute("dislist", dislist);
		model.addAttribute("form", new DiscountForm());
		return "admin/ECS-DIS001";
	}

	@PostMapping("/add-discount")
    public String addDiscount(@ModelAttribute("form") @Validated DiscountForm form, BindingResult br,
                              RedirectAttributes ra, ModelMap model) {
        List<Discount> dislist = discountService.findAll();
        model.addAttribute("dislist", dislist);
        if (br.hasErrors()) {
            return "admin/ECS-DIS001";
        }

        List<Discount> list = discountService.findByDiscountName(form.getDiscountName());
        if (list.size() != 0) {
            model.addAttribute("error", "Discount name already exits!");
            return "admin/ECS-DIS001";
        } else {
            Discount d = new Discount();
            d.setDiscountName(form.getDiscountName());
            d.setDiscountPercent(form.getDiscountPercent());
            discountService.save(d);
            List<Discount> l = discountService.findByDiscountName(d.getDiscountName());
            if (l.size() > 0) {
                ra.addFlashAttribute("success", "Discount successful added");
            }
        }
        return "redirect:/admin/discount";
    }

    @GetMapping("/update-discount")
    public ModelAndView editDiscountForm(@RequestParam("id") Integer id) {
        Discount doption = discountService.findById(id).get();
        DiscountForm form = new DiscountForm();
        form.setId(doption.getId());
        form.setDiscountName(doption.getDiscountName());
        form.setDiscountPercent(doption.getDiscountPercent());

        return new ModelAndView("admin/ECS-DIS001", "form", form);
    }

    @PostMapping("/update-discount")
    public String editDiscount(@ModelAttribute("form") @Validated DiscountForm dform, BindingResult br,
            RedirectAttributes ra,
            ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-DIS001";
        }

        Discount doption = discountService.findById(dform.getId()).get();
        doption.setDiscountName(dform.getDiscountName());
        doption.setDiscountPercent(dform.getDiscountPercent());

        discountService.save(doption);

        List<Discount> l = discountService.findByDiscountName(doption.getDiscountName());
        if (l.size() > 0) {
            ra.addFlashAttribute("success", "Discount successfully updated");
        }
        return "redirect:/admin/discount";
    }

    @GetMapping("/delete-discount")
    public String deleteDiscount(@RequestParam("id") Integer id, RedirectAttributes ra) {
        discountService.deleteById(id);
        ra.addFlashAttribute("success", "Delete successful");
        return "redirect:/admin/discount";
    }


}