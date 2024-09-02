package kr.co.mrlee.story.service;

import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import kr.co.mrlee.story.dto.request.auth.SignInGuestRequestDto;
import kr.co.mrlee.story.dto.request.auth.SignInRequestDto;
import kr.co.mrlee.story.dto.request.auth.SignUpRequestDto;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInGuestResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignUpResponseDto;

public interface AuthService {

	ResponseEntity<? super SignUpResponseDto> signUp(@Valid SignUpRequestDto dto);

	ResponseEntity<? super SignInResponseDto> signIn(@Valid SignInRequestDto dto);

	ResponseEntity<? super SignInGuestResponseDto> signIn(@Valid SignInGuestRequestDto dto);

	ResponseEntity<ResponseDto> deleteMember(String token);

}
