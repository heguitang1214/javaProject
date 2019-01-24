package com.zero.configserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @ClassName WebSecurityConfigurerAdapter
 * @Description TODO
 * @Author 云析-路飞
 * @Date 2018/5/9 19:27
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        super.configure(http);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("ACTUATOR","ADMIN");
    }


    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }




}
