package com.zero.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService(){
		return new MyUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new MyPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**","/img/**","/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().and().authorizeRequests()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/signIn")
			.loginProcessingUrl("/login").defaultSuccessUrl("/index",true)
			.failureUrl("/signIn?error").permitAll()
			.and().sessionManagement().invalidSessionUrl("/signIn")
			.and().rememberMe().tokenValiditySeconds(1209600)
			.and().logout().logoutSuccessUrl("/signIn").permitAll()
			.and().csrf().disable();
	}
}
