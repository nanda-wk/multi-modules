package com.teamyear.site.util;

import com.teamyear.common.entity.Customer;
import com.teamyear.site.security.CUSUserDetails;
import com.teamyear.site.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class CustomerUtil {

    @Autowired
    private CustomerService customerService;

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public static void sendVerificationEmail(HttpServletRequest request, Customer customer) throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl mailSender = mailSender();

        String toAddress = customer.getEmail();
        String fromAddress = "team.year.ecommerce@gmail.com";
        String senderName = "Team Y.E.A.R";
        String subject = "Please verify your registration";

        String content = "Hi [[name]],<br>" +
                "<br>" +
                "Thanks for getting started with our E-Commerce!<br>" +
                "<br>" +
                "We need a little more information to complete your registration, including a confirmation of your email address.<br>" +
                "<br>" +
                "Click below to confirm your email address:<br>" +
                "<br>" +
                "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3><br>" +
                "<br>" +
                "If you have problems, please paste the above URL into your web browser.";

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", customer.getFullName());

        String verifyURL = getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);

    }

    public static void sendResetPasswordToken(HttpServletRequest request, Customer customer) throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl mailSender = mailSender();
        String toAddress = customer.getEmail();
        String fromAddress = "team.year.ecommerce@gmail.com";
        String senderName = "Team Y.E.A.R";
        String subject = "Password Reset.";
        String content = "Hello [[name]],<br>" +
                "<br>" +
                "Somebody requested a new password for the Team YEAR Ecommerce's account.<br>" +
                "<br>" +
                "No changes have been made to your account yet.<br>" +
                "<br>" +
                "You can reset your password by clicking the link below:<br>" +
                "<br><h3><a href=\"[[URL]]\" target=\"_self\">RESET</a></h3><br>" +
                "<br>" +
                "If you did not request a new password, please let us know immediately by replying to this email.<br>" +
                "<br>" +
                "Yours,<br>" +
                "The Team Y.E.A.R.";

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", customer.getFullName());
        String verifyURL = getSiteURL(request) + "/reset?token=" + customer.getResetPasswordToken();
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);
    }

    public static JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("team.year.ecommerce@gmail.com");
        mailSender.setPassword("Te@mYEARE-C0mmerce");
        mailSender.setDefaultEncoding("utf-8");

        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", String.valueOf(true));
        mailProperties.setProperty("mail.smtp.starttls.enable", String.valueOf(true));

        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }

    public static Customer getAuthenticatedCustomer(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            Object principal = request.getUserPrincipal();
            CUSUserDetails userDetails = null;
            if (principal instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
                userDetails = (CUSUserDetails) token.getPrincipal();
            }
            assert userDetails != null;
            return userDetails.getCustomer();
        }
    }

}