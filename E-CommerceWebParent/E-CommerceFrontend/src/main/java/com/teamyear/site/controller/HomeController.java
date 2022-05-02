package com.teamyear.site.controller;

import com.teamyear.common.entity.Announcement;
import com.teamyear.common.entity.Product;
import com.teamyear.site.service.AnnouncementService;
import com.teamyear.site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/")
    public String home(ModelMap model) {
        List<Announcement> aList = announcementService.findAll();
        List<Product> productList = productService.findByThreeRow();
        model.addAttribute("anmList", aList);
        model.addAttribute("productList", productList);
        return "frontend/ECS-WEB001";
    }

    @GetMapping("/about")
    public String about() {
        return "frontend/ECS-WEB002";
    }


    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "frontend/ECS-CUS001";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();

        if (authentication != null) {
            handler.logout(request, response, authentication);
        }
        ra.addFlashAttribute("message", "You have been logout.");
        return "redirect:/login";
    }
}