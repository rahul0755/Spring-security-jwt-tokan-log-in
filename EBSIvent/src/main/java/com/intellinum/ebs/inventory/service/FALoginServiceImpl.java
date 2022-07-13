package com.intellinum.ebs.inventory.service;

import org.springframework.transaction.annotation.Transactional;

import com.intellinum.ebs.inventory.Repository.AuthUserRepository;
import com.intellinum.ebs.inventory.model.XxfndUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackFor = { Throwable.class })
public class FALoginServiceImpl implements FALoginService {

	@Autowired
	EntityManager em;

	@Autowired
	AuthUserRepository au;

	@Override
	public String validateLogin(String userName, String password) {
		Query q = em.createNativeQuery(
				"SELECT  fnd_web_sec.validate_login('" + userName + "','" + password + "') FROM dual ");

		String validateResult = q.getSingleResult().toString();

		return validateResult;
	}

	@Override
	public XxfndUser findByUserName(String userName) {
		Query q = em.createQuery("select fu from XxfndUser fu where UPPER(fu.userName)='" + userName + "' ");

		return (XxfndUser) q.getSingleResult();
	}

	
	@Override
	public List<Object> findUserName(String userName) {
		Query q = em.createNativeQuery("SELECT usr.user_name,get_pwd.decrypt "
				+ "((SELECT (SELECT get_pwd.decrypt (fnd_web_sec.get_guest_username_pwd, "
				+ "usertable.encrypted_foundation_password )FROM DUAL) AS apps_password "
				+ "FROM fnd_user usertable  WHERE usertable.user_name = "
				+ "(SELECT SUBSTR(fnd_web_sec.get_guest_username_pwd, 1,INSTR "
				+ "(fnd_web_sec.get_guest_username_pwd,'/')- 1) "
				+ "FROM DUAL)),usr.encrypted_user_password "
				+ ") PASSWORD FROM fnd_user usr  WHERE usr.user_name = '"+ userName +"' ");
		
		return castList(Object.class, q.getResultList());
	}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

	

}
