package com.boot.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.board.domain.User;
import com.boot.board.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	UserMapper usermapper;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	public void createUser(User user) {
		usermapper.createUser(user);

	}

	public void createAuth(User user) {
		usermapper.createAuth(user);
	}

    public boolean userExist(String username) {
    	return usermapper.userExist(username) !=null;
    }
    
    public boolean passMatch(String username, String rawPassword) {
        User user = usermapper.userExist(username);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, user.getPassword()); //입력 password 를 암호화 후 DB password 와 비교 
    }

}
