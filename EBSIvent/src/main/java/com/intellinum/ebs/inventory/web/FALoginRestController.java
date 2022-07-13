package com.intellinum.ebs.inventory.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intellinum.ebs.inventory.config.security.JwtTokenUtil;
import com.intellinum.ebs.inventory.model.XxfndUser;
import com.intellinum.ebs.inventory.service.FALoginService;
import com.intellinum.ebs.inventory.web.dto.AuthRequest;
import com.intellinum.ebs.inventory.web.dto.AuthResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/main")
public class FALoginRestController {

	@Autowired
	FALoginService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;


	@PostMapping(value = "/validateLogin")
	public ResponseEntity<Object> validateLogin1(@RequestBody @Valid AuthRequest authRequest) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername().toUpperCase(), authRequest.getPassword()));

			final String result = service.validateLogin(authRequest.getUsername(), authRequest.getPassword());
			if (result == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			if (result.equals("Y")) {
				XxfndUser user = service.findByUserName(authRequest.getUsername().toUpperCase());

				final String token = jwtTokenUtil.generateAccessToken(user);
				final AuthResponse authResponse = new AuthResponse();
				authResponse.setUsername(user.getUserName());
				authResponse.setToken(token);
				authResponse.setId(String.valueOf(user.getUserId()));

				return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(authResponse);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Username or Password");
			}

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Username or Password");
		}
	}
}

