package kr.co.mrlee.story.service;

import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.dto.response.user.GetSignInUserResponseDto;

public interface UserService {

	ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String token);

}
