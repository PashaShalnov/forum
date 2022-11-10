package org.telran.forum.accounting.dto;

import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDto {
	@Id
	String login;
	String firstName;
	String lastName;
	@Singular
	Set<String> roles;

}
