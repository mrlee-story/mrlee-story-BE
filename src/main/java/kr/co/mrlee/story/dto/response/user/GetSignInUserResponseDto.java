package kr.co.mrlee.story.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInGuestResponseDto;
import lombok.Getter;

@Getter
public class GetSignInUserResponseDto extends ResponseDto {
	
	private UserVO loginUser;
	private int authorizationLevel;
	
	private GetSignInUserResponseDto(UserVO user) {
		super(ResponseStatus.SUCCESS);
		this.loginUser = user;
		this.authorizationLevel = user.getAuthorizationLevel();
	}

	public static ResponseEntity<? super GetSignInUserResponseDto> success(UserVO user) {
		GetSignInUserResponseDto result = new GetSignInUserResponseDto(user);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<? super GetSignInUserResponseDto> authorizationFail() {
		ResponseDto result = new ResponseDto(ResponseStatus.AUTHORIZATION_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}

	public static ResponseEntity<? super GetSignInUserResponseDto> noExistUser() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
	
}
