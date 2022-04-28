package com.teamyear.admin.controller;

import com.teamyear.admin.service.AnnouncementService;
import com.teamyear.admin.service.ProductService;
import com.teamyear.common.entity.Announcement;
import com.teamyear.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/admin")
    public String home() {
        return "admin/ECS-ADM001";
    }

    @GetMapping("/admin/login")
    public String loginView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "admin/ECS-ADM002";
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();

        if (authentication != null) {
            handler.logout(request, response, authentication);
        }
        ra.addFlashAttribute("message", "You have been logout.");
        return "redirect:/admin/login";
    }
}