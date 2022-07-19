package com.ween.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.httpBasic().and()
				.authorizeRequests()
				.antMatchers("/")
				.hasRole("ADMIN")
				.anyRequest()
				.authenticated().and().build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return (web -> web.ignoring().antMatchers("/index","/index.html"));
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService(){
		UserDetails user= User.builder()
				.username("admin")
				.password("admin123")
				.roles("ADMIN")
				.passwordEncoder(passwordEncoder()::encode)
				.build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
