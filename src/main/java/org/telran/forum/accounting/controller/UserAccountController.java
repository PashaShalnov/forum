package org.telran.forum.accounting.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telran.forum.accounting.dto.NewUserDto;
import org.telran.forum.accounting.dto.RolesRespDto;
import org.telran.forum.accounting.dto.UpdateUserDto;
import org.telran.forum.accounting.dto.UserDto;
import org.telran.forum.accounting.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
	
	final AccountService accountService;
	
	@PostMapping("/register")
	public UserDto addUser(@RequestBody NewUserDto newUserDto) {
		return accountService.addUser(newUserDto);
	}
	
	@GetMapping()
	public UserDto findUserByLogin(String login) {
		return accountService.findUser(login);
	}
	
	@PostMapping("/login")
	public UserDto login(@RequestBody String login, @RequestBody String password) {
		return accountService.login(login, password);
	}
	
	@DeleteMapping("/user/{login}")
	public UserDto removeUser(@PathVariable String login) {
		return accountService.removeUser(login);
	}
	
	@PutMapping("/user/{login}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) {
		return accountService.updateUser(login, updateUserDto);
	}
	
	@PutMapping("/user/{login}/role/{role}")
	public RolesRespDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRole(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public RolesRespDto removeRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRole(login, role, false);
	}
	
	
//	@PutMapping("/user/{login}/role/{role}")
//	public RolesRespDto addRole(@PathVariable String login, @PathVariable String role) {
//		return accountService.addRole(login, role);
//	}
//
//	@PutMapping("/user/{login}/role/{role}")
//	public RolesRespDto removeRole(@PathVariable String login, @PathVariable String role) {
//		return accountService.removeRole(login, role);
//	}
	
	@PutMapping("/password")
	public boolean changePassword(@RequestBody String login,@RequestBody String password) {
		return accountService.changePassword(login, password);
	}
}
