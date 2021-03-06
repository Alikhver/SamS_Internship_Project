package com.alikhver.web.config;

import com.alikhver.web.security.JwtConfigurer;
import com.alikhver.web.security.handler.CustomAuthenticationSuccessHandler;
import com.alikhver.web.security.handler.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfigurer jwtConfigurer;
    @Autowired
    private CustomAuthenticationSuccessHandler authSuccessHandler;
    @Autowired
    private CustomLogoutHandler customLogoutHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                .antMatchers("/", "/register", "/auth/*", "/profile/*",
                        "/profiles/createUserAndProfile", "/profiles/phoneNumberExists", "/profiles/emailExists",
                        "/users/loginExists")
                                .permitAll()
                .antMatchers("/login", "/auth/login", "/organisation/*", "/authenticate",
                        "/organisation/*/workers", "/organisation/*/utilities", "/organisation/*/records", "/organisations", "/records/*/book", "/createOrganisation")
                                .permitAll()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**")
                                .permitAll()
                .anyRequest().authenticated()
                    .and()
                .formLogin()
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .usernameParameter("login")
                                .passwordParameter("password")
                                .successHandler(authSuccessHandler)
                    .and()
                .logout()
                                .logoutUrl("/logout")
                                .deleteCookies("Authorization")
                                .deleteCookies("profileId")
                                .addLogoutHandler(customLogoutHandler)
                    .and()
                .apply(jwtConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
