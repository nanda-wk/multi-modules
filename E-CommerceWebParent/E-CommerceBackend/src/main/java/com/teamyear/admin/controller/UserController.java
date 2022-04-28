package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.UserForm;
import com.teamyear.admin.service.RoleService;
import com.teamyear.admin.service.UserService;
import com.teamyear.common.entity.Role;
import com.teamyear.common.entity.User;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("userList")
    public List<User> userList() {
        return userService.findAll();
    }

    @ModelAttribute("roleList")
    public List<Role> roleList() {
        return roleService.findAll();
    }

    @GetMapping("/users")
    public String users(ModelMap model) {

        model.addAttribute("form", new UserForm());
        return "admin/ECS-USR002";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute("form") @Validated UserForm form, BindingResult br, RedirectAttributes ra, ModelMap model) {
        if (br.hasErrors()) {
            return "admin/ECS-USR002";
        } else if (userService.getUserByUsername(form.getUsername()) != null) {
            model.addAttribute("uerror", "Username already exist.");
            return "admin/ECS-USR002";
        } else if (!form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("error", "Confirm password do not match.");
            return "admin/ECS-USR002";
        }

        User user = new User();
        user.setName(form.getName());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        for (Role role : form.getPermission()) {
            user.addRole(role);
        }

        userService.save(user);
        ra.addFlashAttribute("success", "User successfully added.");

        return "redirect:/admin/users";
    }

    @GetMapping("/update-user")
    public String updateUserForm(@RequestParam("id") Integer id, ModelMap model) {
        User user = userService.findById(id).get();
        UserForm form = new UserForm();
        form.setId(user.getId());
        form.setName(user.getName());
        form.setUsername(user.getUsername());
        form.setPermission(user.getRoles());
        model.addAttribute("form", form);
        return "admin/ECS-USR002";
    }

    @PostMapping("/update-user")
    public String updateUser(@RequestParam("id") Integer id,
                             @ModelAttribute("form") @Validated UserForm form, BindingResult br,
                             RedirectAttributes ra, ModelMap model) {
        if(br.hasErrors()){
            return "admin/ECS-USR002";
        } else if (!form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("error", "Confirm password do not match.");
            return "admin/ECS-USR002";
        }

        User user = userService.findById(id).get();
        if(!user.getUsername().equals(form.getUsername())){
            User userList = userService.getUserByUsername(form.getUsername());
            if(userList != null){
                model.addAttribute("uerror", "Username already exist!");
                return "admin/ECS-USR002";
            }
        }

        user.setName(form.getName());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());

        userService.save(user);

        ra.addFlashAttribute("success", "Update successful.");
        return "redirect:/admin/users";
    }

    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam("id") Integer id, RedirectAttributes ra){
        User user = userService.findById(id).get();
        userService.deleteById(id);
        ra.addFlashAttribute("success", user.getName() + " successfully deleted.");

        return "redirect:/admin/users";
    }
}