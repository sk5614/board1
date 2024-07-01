package com.boot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.board.domain.Board;
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

	public void createAuthorities(User user) {
	    usermapper.createAuthority(user);
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

	public User infoUser(User user) {
		return usermapper.userExist(user);
	}

	public int countUser() {
		return usermapper.countUser();
	}
	
	public List<User> userList(int page, int size) {
		int offset = (page-1)*size;
		return usermapper.userList(size,offset);
	}
	
	public void editAuthority(User user) {
		usermapper.editAuthority(user);
	}

}
