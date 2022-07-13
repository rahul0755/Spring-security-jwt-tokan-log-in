package com.intellinum.ebs.inventory.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intellinum.ebs.inventory.model.XxfndUser;

@Repository
public interface AuthUserRepository  extends  JpaRepository<XxfndUser, Long>{

	public Optional<XxfndUser> findByuserName(String userName);

}
