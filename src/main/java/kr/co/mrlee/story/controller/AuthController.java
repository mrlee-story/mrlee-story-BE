package kr.co.mrlee.story.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.mrlee.story.dto.request.auth.SignInGuestRequestDto;
import kr.co.mrlee.story.dto.request.auth.SignInRequestDto;
import kr.co.mrlee.story.dto.request.auth.SignUpRequestDto;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInGuestResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignUpResponseDto;
import kr.co.mrlee.story.provider.JwtProvider;
import kr.co.mrlee.story.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
	}
	
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
    
    @PostMapping("/sign-in/guest")
    public ResponseEntity<? super SignInGuestResponseDto> signIn(@RequestBody @Valid SignInGuestRequestDto requestBody) {
    	ResponseEntity<? super SignInGuestResponseDto> response = authService.signIn(requestBody);
    	return response;
    }
    
    @DeleteMapping("")
    public ResponseEntity<ResponseDto> deleteMember(@RequestHeader HttpHeaders headers) {
		String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
		
    	ResponseEntity<ResponseDto> response = authService.deleteMember(token);
    	return response;
    }
	
	
}
