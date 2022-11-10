package org.telran.forum.accounting.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@EqualsAndHashCode(of = "login")
@Document(collection = "users")
public class UserAccount {
	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	@Singular
	Set<String> roles;
	
	public UserAccount() {
		roles = new HashSet<>((Arrays.asList("USER")));
	}
	
	public UserAccount(String login, String password, String firstName, String lastName) {
		this();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = new HashSet<>(Arrays.asList("USER"));
	}
	
	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}

	public UserAccount(Set<String> roles) {
		this.roles = roles;
	}
}
