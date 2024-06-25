package com.boot.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.board.domain.User;

@Mapper
public interface UserMapper {
	
	public void createuser(User user);

	public void createAuthority(User user);
	
}
