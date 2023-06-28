package com.movies.app.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration of Spring Security
 * 
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Creates admin with admin role
	 */
	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {

		UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN")
				.build();
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);

	}

	/**
	 * Filter that states which endpoint should be visible to all
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.headers().frameOptions().disable();

		http.formLogin()/** .loginPage("/customLogin") */
				.permitAll().and().logout().logoutUrl("/logout").logoutSuccessUrl("/index")
				.deleteCookies("remember-me");

		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/index", "/customLogin", "/movies", "/movies/**", "/error/**", "/login", "/login/**",
					"/images/**", "/images/icons/**", "/images/poster/**").permitAll();
			auth.requestMatchers("/moviesUI/**", "/moviesFolder/**", "/resources/**", "/static/**", "/css/**",
					"/static/js/**", "/webjars/**", "/moviesUI", "/moviesUI/**").authenticated();
			auth.requestMatchers("/admin", "/newMovie", "/addMovie").hasRole("ADMIN");
		}).httpBasic(Customizer.withDefaults()).build();

	}
}
