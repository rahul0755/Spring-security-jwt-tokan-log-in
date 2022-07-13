package com.intellinum.ebs.inventory.service;


import java.util.List;
import com.intellinum.ebs.inventory.model.XxfndUser;

public interface FALoginService {
	
	
	public String validateLogin(String userName, String password);
	
	public XxfndUser findByUserName(String userName);
	
	public List<Object> findUserName(String userName);

	//public Optional<XxfndUser>  findUserName(String userName);
}
