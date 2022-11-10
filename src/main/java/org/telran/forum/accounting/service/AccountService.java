package org.telran.forum.accounting.service;

import org.telran.forum.accounting.dto.NewUserDto;
import org.telran.forum.accounting.dto.RolesRespDto;
import org.telran.forum.accounting.dto.UpdateUserDto;
import org.telran.forum.accounting.dto.UserDto;

public interface AccountService {
	
	UserDto addUser(NewUserDto newUserDto);
	
	UserDto findUser(String login);
	
	UserDto login(String token);
	
	UserDto removeUser(String login);
	
	UserDto updateUser(String login, UpdateUserDto updateUserDto);
	
	RolesRespDto changeRole(String login, String role, boolean addRole);
		
	void changePassword(String login, String password);
}
