package org.telran.forum.accounting.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Singular;

@Getter
public class RolesRespDto {
	String login;
	@Singular
	Set<String> roles;

}
