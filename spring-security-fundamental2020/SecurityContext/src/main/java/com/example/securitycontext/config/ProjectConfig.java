package com.example.securitycontext.config;

import com.example.securitycontext.security.filters.TokenAuthFilter;
import com.example.securitycontext.security.filters.UsernamePasswordAuthFilter;
import com.example.securitycontext.security.providers.OtpAuthenticationProvider;
import com.example.securitycontext.security.providers.TokenAuthProvider;
import com.example.securitycontext.security.providers.UsernamePasswordAuthProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsernamePasswordAuthProvider authProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private TokenAuthProvider tokenAuthProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider)
            .authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(tokenAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) {
        http.addFilterAt(usernamePasswordAuthFilter(),
                BasicAuthenticationFilter.class)
            .addFilterAfter(tokenAuthFilter(),
                BasicAuthenticationFilter.class );
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter();
    }

    @Bean
    public UsernamePasswordAuthFilter usernamePasswordAuthFilter() {
        return new UsernamePasswordAuthFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public InitializingBean initializingBean() {
        return () -> {
            SecurityContextHolder.setStrategyName(
                    SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }
}
