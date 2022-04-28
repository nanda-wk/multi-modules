package com.teamyear.site.controller;

import com.teamyear.common.entity.City;
import com.teamyear.common.entity.Customer;
import com.teamyear.common.entity.Region;
import com.teamyear.common.entity.Township;
import com.teamyear.site.formmodel.CustomerForm;
import com.teamyear.site.service.CityService;
import com.teamyear.site.service.CustomerService;
import com.teamyear.site.service.RegionService;
import com.teamyear.site.service.TownshipService;
import com.teamyear.site.util.CustomerUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TownshipService townshipService;

    @ModelAttribute("regionList")
    public List<Region> regionList() {
        return regionService.findAll();
    }

    @ModelAttribute("cityList")
    public List<City> cityList() {
        return cityService.findAll();
    }

    @ModelAttribute("townshipList")
    public List<Township> townshipList() {
        return townshipService.findAll();
    }

    @GetMapping("/register")
    public ModelAndView customerRegister() {
        return new ModelAndView("frontend/ECS-CUS002", "form", new CustomerForm());
    }

    @PostMapping("/add-customer")
    public String addCustomer(@ModelAttribute("form") @Validated CustomerForm form, BindingResult br, ModelMap model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if (br.hasErrors()) {
            return "frontend/ECS-CUS002";
        } else if (customerService.checkUsername(form.getUsername())) {
            model.addAttribute("errorUsername", "Username already exist.");
            return "frontend/ECS-CUS002";
        } else if (customerService.checkEmail(form.getEmail())) {
            model.addAttribute("errorEmail", "Email already exist.");
            return "frontend/ECS-CUS002";
        } else if (!form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("errorPwd", "Password do not match.");
            return "frontend/ECS-CUS002";
        }

        String randomCode = RandomString.make(64);

        Customer customer = new Customer();
        customer.setFirstName(form.getFirstName());
        customer.setLastName(form.getLastName());
        customer.setUsername(form.getUsername());
        customer.setPassword(form.getPassword());
        customer.setEmail(form.getEmail());
        customer.setDob(Date.valueOf(form.getDob()));
        customer.setGender(form.getGender());
        customer.setPhone(form.getPhone());
        customer.setRole("Customer");
        customer.setRegion(form.getRegion());
        customer.setCity(form.getCity());
        customer.setTownship(form.getTownship());
        customer.setAddress(form.getAddress1());
        customer.setAddressOpl(form.getAddress2());
        customer.setEnable(false);
        customer.setVerificationCode(randomCode);

        customerService.save(customer);

        CustomerUtil.sendVerificationEmail(request, customer);

        model.addAttribute("success", "Register successful.");
        return "frontend/success";
    }

    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam("code") String code) {
        if (customerService.verify(code)) {
            return "frontend/register_success";
        } else {
            return "frontend/verify_fail";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "frontend/forgot_pwd";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordProcess(@RequestParam(name = "email") String email, HttpServletRequest request, ModelMap model) throws Exception {
        Customer customer = customerService.resetPasswordToken(email);

        CustomerUtil.sendResetPasswordToken(request, customer);

        model.addAttribute("message", "We have sent reset token to your email.");
        return "frontend/forgot_pwd";
    }

    @GetMapping("/reset")
    public String resetPasswordForm(@RequestParam(name = "token") String token, ModelMap model) {
        if (customerService.findByResetPasswordToken(token) != null) {
            model.addAttribute("token", token);
            return "frontend/update_pwd";
        } else {
            return "frontend/verify_fail";
        }
    }

    @PostMapping("/reset-password")
    public String resetPasswordForm(@RequestParam(name = "token") String token,
                                    @RequestParam(name = "password") String password,
                                    @RequestParam(name = "confirm") String confirm, ModelMap model) throws Exception {
        if (!password.equals(confirm)) {
            model.addAttribute("error", "password do not match");
            return "frontend/update_pwd";
        }

        customerService.updatePassword(token, password);

        return "frontend/success_reset";
    }

}