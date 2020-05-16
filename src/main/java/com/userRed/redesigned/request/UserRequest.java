package com.userRed.redesigned.request;

import com.userRed.redesigned.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

	String username;
	String password;
	String email;
	Gender gender;
}
