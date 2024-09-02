package kr.co.mrlee.story.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.mrlee.story.dto.response.user.GetSignInUserResponseDto;
import kr.co.mrlee.story.provider.JwtProvider;
import kr.co.mrlee.story.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(@RequestHeader(JwtProvider.AUTHORIZATION_HEADER_KEY) String token) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(token);
        return response;
    }
}
