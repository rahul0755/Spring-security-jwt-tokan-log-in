package com.intellinum.ebs.inventory.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.intellinum.ebs.inventory.service.security.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasLength;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService userRepo;

	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		jwtTokenUtil = new JwtTokenUtil();

		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !hasLength(header) || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();
		// Get user identity and set it on the spring security context
		UserDetails userDetails = userRepo.loadUserByUsername(jwtTokenUtil.getUsername(token));

		if (!jwtTokenUtil.validate(token)) {
			chain.doFilter(request, response);
			return;
		}

//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//				userDetails, null,
//				ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(null));

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}