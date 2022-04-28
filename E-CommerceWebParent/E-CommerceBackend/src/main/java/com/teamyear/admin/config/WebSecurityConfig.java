package com.teamyear.admin.config;

import com.teamyear.admin.security.ECSUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasAnyAuthority("Admin", "Brand", "Category", "SubCategory", "Product")
                .antMatchers("/admin/brand", "/admin/add-brand",
                        "/admin/update-brand", "/admin/delete-brand").hasAnyAuthority("Admin", "Brand")
                .antMatchers("/admin/category", "/admin/add-category",
                        "/admin/update-category", "/admin/delete-category").hasAnyAuthority("Admin", "Category")
                .antMatchers("/admin/sub-category", "/admin/add-sub-category",
                        "/admin/update-sub-category", "/delete-sub-category").hasAnyAuthority("Admin", "SubCategory")
                .antMatchers("/admin/products/", "/admin/products",
                        "/admin/add-product", "/admin/add-product/",
                        "/admin/update-product", "/admin/update-product/",
                        "/admin/delete-product", "/admin/delete-product/").hasAnyAuthority("Admin", "Product")
                .antMatchers("/admin/role", "/admin/users",
                        "/admin/add-role", "/admin/update-role", "/admin/delete-role",
                        "/admin/add-user", "/admin/update-user", "/admin/delete-user").hasAuthority("Admin")
                .antMatchers("/admin/region", "/admin/add-region",
                        "/admin/update-region", "/admin/delete-region").hasAuthority("Admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin")
                .usernameParameter("username")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutUrl("/admin/logout")
                .permitAll()
                .and()
                .rememberMe()
                .key("abcdgeifdhgiekKDFIEngei9401257389__")
                .tokenValiditySeconds(7 * 24 * 60 * 60);// 7day 24h 60m 60s
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/adminstyle/**", "/webjars/**", "/frontendStyle/**");
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder())
//                .withUser("admin")
//                .password(encoder().encode("admin"))
//                .authorities("Admin");
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ECSUserDetailsService();
    }
}