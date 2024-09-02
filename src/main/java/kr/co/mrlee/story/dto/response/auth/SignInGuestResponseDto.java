package kr.co.mrlee.story.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.provider.JwtProvider;
import lombok.Getter;

@Getter
public class SignInGuestResponseDto extends ResponseDto {


	private String token;
	private long expirationTime;
	
	private SignInGuestResponseDto(String token) {
		super(ResponseStatus.SUCCESS);
		
		this.token = token;
		this.expirationTime = JwtProvider.EXPIRATION_TIME_IN_MS;
	}
	
	public static ResponseEntity<? super SignInGuestResponseDto> success(String token) {
		SignInGuestResponseDto result = new SignInGuestResponseDto(token);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super SignInGuestResponseDto> authorizationFail() {
		ResponseDto result = new ResponseDto(ResponseStatus.AUTHORIZATION_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}

	public static ResponseEntity<? super SignInGuestResponseDto> signInFail() {
        ResponseDto result = new ResponseDto(ResponseStatus.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}

	public static ResponseEntity<? super SignInGuestResponseDto> noExistBoard() {
        ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
