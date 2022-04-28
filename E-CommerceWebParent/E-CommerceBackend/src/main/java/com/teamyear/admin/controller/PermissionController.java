package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.RoleForm;
import com.teamyear.admin.service.RoleService;
import com.teamyear.common.entity.Role;
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
public class PermissionController {

    @Autowired
    RoleService roleService;

    @ModelAttribute("roleList")
    public List<Role> roleList() {
        return roleService.findAll();
    }

    @GetMapping("/role")
    public String roleForm(ModelMap model) {

        model.addAttribute("form", new RoleForm());
        return "admin/ECS-USR001";
    }

    @PostMapping("/add-role")
    public String addRole(@ModelAttribute("form") @Validated RoleForm form, BindingResult br, RedirectAttributes ra,
            ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-USR001";
        }

        List<Role> rList = roleService.findByRoleName(form.getRoleName());
        if (rList.size() != 0) {
            model.addAttribute("error", "Role already exist!");
            return "admin/ECS-USR001";
        }

        Role role = new Role();
        role.setRoleName(form.getRoleName());
        role.setDescription(form.getDescription());
        roleService.save(role);

        ra.addFlashAttribute("success", "Role Successfully added");
        return "redirect:/admin/role";
    }

    @GetMapping("/update-role")
    public String updateRoleForm(@RequestParam("id") Integer id, ModelMap model) {

        Role role = roleService.findById(id);
        RoleForm form = new RoleForm();
        form.setId(role.getId());
        form.setRoleName(role.getRoleName());
        form.setDescription(role.getDescription());

        model.addAttribute("form", form);
        return "admin/ECS-USR001";
    }

    @PostMapping("/update-role")
    public String updateRole(@RequestParam("id") Integer id, @ModelAttribute("form") RoleForm form, RedirectAttributes ra) {
        Role role = roleService.findById(id);

        if (!role.getRoleName().equals(form.getRoleName())) {
            List<Role> rList = roleService.findByRoleName(form.getRoleName());
            if (rList.size() > 0) {
                return "admin/ECS-USR001";
            }
        }

        role.setRoleName(form.getRoleName());
        role.setDescription(form.getDescription());
        roleService.save(role);
        ra.addFlashAttribute("success", "Update successful.");
        return "redirect:/admin/role";

    }

    @GetMapping("/delete-role")
    public String deleteRole(@RequestParam("id") Integer id, RedirectAttributes ra) {

        roleService.deleteById(id);

        ra.addFlashAttribute("success", "Delete successful!");
        return "redirect:/admin/role";
    }
}