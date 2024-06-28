package com.boot.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.board.domain.User;

@Mapper
public interface UserMapper {
	
	public void createUser(User user);

	public void createAuth(User user);
	
	public User userExist(String username);

	public User userExist(User user);
}
