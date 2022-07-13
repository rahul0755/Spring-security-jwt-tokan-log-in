package com.intellinum.ebs.inventory.config.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;

import com.intellinum.ebs.inventory.service.security.CustomUserDetailsService;

import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private final CustomUserDetailsService userDetailsService;

	private final JwtTokenFilter jwtTokenFilter;

	public SecurityConfig(CustomUserDetailsService userRepo, JwtTokenFilter jwtTokenFilter) {
		super();

		this.userDetailsService = userRepo;
		this.jwtTokenFilter = jwtTokenFilter;

		// Inherit security context in async function calls
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> userDetailsService.loadUserByUsername(username));
	}


	// Set password encoding schema
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

		// Set unauthorized requests exception handler
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			logger.error("Unauthorized request - {}", ex.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();

		// Set permissions on endpoints
		http.authorizeRequests()
				// Swagger endpoints must be publicly accessible
				.antMatchers("/").permitAll()
				
				.antMatchers("/api/main/validateLogin/**").permitAll()
				// Our private endpoints
				.anyRequest().authenticated();

	    //http.addFilterBefore(corsFilter(), SessionManagementFilter.class);
		// Add JWT token filter
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
	

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);

		configuration.setAllowedHeaders(Arrays.asList("*"));
		// configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// Expose authentication manager bean
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}