package kr.co.mrlee.story.dto.request.auth;

import lombok.Getter;

@Getter
public class SignInGuestRequestDto {

	private int userAuthority;
	private String password;
	
	private int contentNumber;
	
	
}
