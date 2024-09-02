package kr.co.mrlee.story.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;

public class IncreaseViewCountResponseDto extends ResponseDto {
	
	private IncreaseViewCountResponseDto() {
		super(ResponseStatus.SUCCESS);
	}
	
	public static ResponseEntity<? super IncreaseViewCountResponseDto> success() {
		IncreaseViewCountResponseDto result = new IncreaseViewCountResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super IncreaseViewCountResponseDto> noExistBoard() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
