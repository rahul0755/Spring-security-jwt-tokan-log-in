package com.intellinum.ebs.inventory.service.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intellinum.ebs.inventory.Repository.AuthUserRepository;
import com.intellinum.ebs.inventory.model.XxfndUser;
import com.intellinum.ebs.inventory.service.FALoginService;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	AuthUserRepository au;

	@Autowired
	FALoginService service;

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		XxfndUser user = au.findByuserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		String encodePassword=null;
		if (user.getUserName() != null && user.getEncryptedUserPassword() != null) {
			List<Object> list = service.findUserName(user.getUserName());
			if (list.size() > 0 ) {
				Iterator<Object> itr = list.iterator();
				String password = null;
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					for (int i = 0; i < obj.length; i++) {
						password = obj[1].toString();
					}
				}
			  encodePassword=passwordEncoder(password);
			}
			return new org.springframework.security.core.userdetails.User(user.getEmailAddress(),
					encodePassword, new ArrayList<>());
		} else {
			return new org.springframework.security.core.userdetails.User(user.getEmailAddress(),
				user.getEncryptedUserPassword(), new ArrayList<>());
		}

	}

	public String passwordEncoder(String pass) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		passwordEncoder.encode(pass);
		return passwordEncoder.encode(pass);
	}
}
