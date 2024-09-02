package kr.co.mrlee.story.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;

public class PatchBoardResponseDto extends ResponseDto {
	private PatchBoardResponseDto() {
		super(ResponseStatus.SUCCESS);
	}

	public static ResponseEntity<? super PatchBoardResponseDto> success() {
		PatchBoardResponseDto result = new PatchBoardResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super PatchBoardResponseDto> noExistBoard() {
        ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	public static ResponseEntity<? super PatchBoardResponseDto> authorizationFail() {
		ResponseDto result = new ResponseDto(ResponseStatus.AUTHORIZATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}

	public static ResponseEntity<? super PatchBoardResponseDto> noExistUser() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}

	public static ResponseEntity<? super PatchBoardResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseStatus.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
	}
}
