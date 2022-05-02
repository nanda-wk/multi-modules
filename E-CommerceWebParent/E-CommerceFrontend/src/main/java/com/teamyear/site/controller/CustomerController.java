package com.teamyear.site.controller;

import com.teamyear.common.entity.*;
import com.teamyear.site.formmodel.CustomerForm;
import com.teamyear.site.service.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private OrderService orderService;

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
        customer.setDob(form.getDob());
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

    @GetMapping("/update-profile")
    public String customerUpdate(HttpServletRequest request, ModelMap model) {
        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);
        CustomerForm form = new CustomerForm();

        form.setFirstName(customer.getFirstName());
        form.setLastName(customer.getLastName());
        form.setEmail(customer.getEmail());
        form.setDob(customer.getDob());
//        form.setGender(customer.getGender());
        form.setPhone(customer.getPhone());
//        form.setRegion(customer.getRegion());
        form.setCity(customer.getCity());
        form.setTownship(customer.getTownship());
        form.setAddress1(customer.getAddress());
        form.setAddress2(customer.getAddressOpl());

        model.addAttribute("form", form);
        return "frontend/ECS-CUS003-01";
    }

    @PostMapping("/update-profile")
    public String updateProfile(HttpServletRequest request, @ModelAttribute("form") CustomerForm form, BindingResult br, RedirectAttributes ra) {
//        if (br.hasErrors()) {
//            return "frontend/ECS-CUS003-01";
//        }
        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);
        customer.setFirstName(form.getFirstName());
        customer.setLastName(form.getLastName());
        customer.setEmail(form.getEmail());
        customer.setDob(form.getDob());
//        customer.setGender(form.getGender());
        customer.setPhone(form.getPhone());
//        customer.setRegion(form.getRegion());
        customer.setCity(form.getCity());
        customer.setTownship(form.getTownship());
        customer.setAddress(form.getAddress1());
        customer.setAddressOpl(form.getAddress2());

        customerService.save(customer);

        ra.addFlashAttribute("success", "Customer update successful.");
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String customerProfile(HttpServletRequest request, ModelMap model) {

        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);

        model.addAttribute("customer", customer);
        return "frontend/ECS-CUS003";
    }

    @GetMapping("/my-orders")
    public String customerOrders(HttpServletRequest request, ModelMap model) {

        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);
        List<Orders> ordersList = orderService.findOrdersByCustomer(customer);

        model.addAttribute("ordersList", ordersList);
        return "frontend/";
    }

    @GetMapping("/order-details")
    public String orderDetail(HttpServletRequest request, @RequestParam("id") String orderId, ModelMap model) {

        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);

        Orders orders = orderService.findByCustomerAndOrderId(customer, orderId);

        model.addAttribute("orderDetails", orders);
        return "frontend/";
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