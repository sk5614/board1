package com.boot.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.board.domain.User;

@Mapper
public interface UserMapper {
	
	public void createUser(User user);

	
	public User userExist(String username);

	public User userExist(User user);

	public void createAuthority(User user);
	
	public int countUser();
	
	public List<User> userList(@Param("limit") int limit,@Param("offset") int offset);
	
	public void editAuthority(User user);
}
