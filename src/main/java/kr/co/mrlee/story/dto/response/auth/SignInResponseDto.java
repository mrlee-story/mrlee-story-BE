package kr.co.mrlee.story.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.provider.JwtProvider;
import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {
	
	private String token;
	private long expirationTime;
	
	private SignInResponseDto(String token) {
		super(ResponseStatus.SUCCESS);
		this.token = token;
		this.expirationTime = JwtProvider.EXPIRATION_TIME_IN_MS;
	}
	
    public static ResponseEntity<SignInResponseDto> success(String token) {
        SignInResponseDto result = new SignInResponseDto(token);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto result = new ResponseDto(ResponseStatus.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
