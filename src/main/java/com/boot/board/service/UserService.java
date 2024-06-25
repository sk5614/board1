package com.boot.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.board.domain.User;
import com.boot.board.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	UserMapper usermapper;

	public void createUser(User user) {
		usermapper.createuser(user);

	}

	public void createAuthorities(User user) {
		usermapper.createAuthority(user);
	}

}
