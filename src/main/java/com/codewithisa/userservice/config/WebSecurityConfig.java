package com.codewithisa.userservice.config;

import com.codewithisa.userservice.entity.enumeration.ERoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        "/api/auth/**",
//                        "/users/add-user",
                        "/films/sedang-tayang",
                        "/schedules/**"
//                        "/swagger-ui/**",
//                        "/v3/api-docs/**"
                ).permitAll()
                .antMatchers(
                        "/swagger-ui/index.html",
                        "/swagger-ui.html",
                        "/v3/api-docs/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(
                        "/films/add-film",
                        "/films/update-film-name/**",
                        "/films/delete-film/**"
//                ).hasAuthority(ERoles.ADMIN.name())
                ).permitAll()
                .antMatchers(
                        "/users/update-user/**",
                        "/users/**",
                        "/users/delete-user/**",
                        "/invoice/generate-tiket"
//                ).hasAuthority(ERoles.CUSTOMER.name())
                ).permitAll()
//                .antMatchers("/files/**").hasAuthority(ERoles.ADMIN.name())
//                .antMatchers("/api/test/**").permitAll()
                .anyRequest().authenticated();
    }
}
